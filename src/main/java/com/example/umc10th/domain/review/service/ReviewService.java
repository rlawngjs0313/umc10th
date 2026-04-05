package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.StoreException;
import com.example.umc10th.domain.mission.exception.code.StoreErrorCode;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.converter.GeneralConverter;
import com.example.umc10th.global.dto.GeneralDTO;
import com.example.umc10th.global.security.entity.AuthMember;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    // 리뷰 생성
    @Transactional
    public ReviewResDTO.CreateReview createReview(
            AuthMember member,
            ReviewReqDTO.CreateReview dto
    ) {

        // 가게 찾기
        Store store = storeRepository.findById(dto.storeId())
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        // 리뷰 엔티티 생성
        Review review = ReviewConverter.toReview(store, member.getMember(), dto);

        // 저장
        reviewRepository.save(review);

        return ReviewConverter.toCreateReview(review);
    }

    // 리뷰 조회
    public GeneralDTO.Pagination<ReviewResDTO.GetReview> getReviews(
            Long storeId,
            Integer pageSize,
            String cursor
    ) {

        // 가게가 있는지 확인
        if (!storeRepository.existsById(storeId)) throw new StoreException(StoreErrorCode.NOT_FOUND);

        // PageRequest 생성
        PageRequest pageRequest = PageRequest.ofSize(pageSize);

        // 상점 ID로 리뷰 목록들 조회
        Slice<Review> reviewList;
        if (!cursor.equals("-1")){
            Long idCursor;
            try{
                idCursor = Long.parseLong(cursor);
            } catch (NumberFormatException e){
                throw new StoreException(StoreErrorCode.NOT_VALID_CURSOR);
            }

            reviewList = reviewRepository.findAllByStore_IdAndIdLessThanOrderByIdDesc(storeId, idCursor, pageRequest);
        } else {
            reviewList = reviewRepository.findAllByStore_IdOrderByIdDesc(storeId, pageRequest);
        }

        // 조회 결과가 0인 경우
        if (!reviewList.hasContent()){
            return GeneralConverter.toPagination(
                    List.of(),
                    false,
                    "-1",
                    pageSize,
                    "id"
            );
        }

        // 다음 커서 생성
        String nextCursor = reviewList.getContent().getLast().getId().toString();
        
        return GeneralConverter.toPagination(
                reviewList.getContent().stream()
                        .map(ReviewConverter::toGetReview)
                        .toList(),
                reviewList.hasNext(),
                nextCursor,
                reviewList.getSize(),
                "id"
        );
    }

    // 내가 작성한 리뷰 목록 조회하기
    public GeneralDTO.Pagination<ReviewResDTO.GetMyReview> getMyReview(
            AuthMember member,
            @Nullable Long storeId,
            Integer pageSize,
            String cursor,
            String cursorType
    ) {
        // 커서 타입 소문자로
        cursorType = cursorType.toLowerCase();

        // 가게가 있는지 확인
        if (storeId != null && storeRepository.existsById(storeId)) throw new StoreException(StoreErrorCode.NOT_FOUND);

        // PageRequest 생성
        PageRequest pageRequest = PageRequest.ofSize(pageSize);

        // 커서 분해 (XXX:ID)
        Long idCursor = null;
        BigDecimal prevCursor = null;
        if (!cursor.equals("-1") && cursor.contains(":")){
            try {
                prevCursor = BigDecimal.valueOf(Double.parseDouble(cursor.split(":")[0]));
                idCursor = Long.parseLong(cursor.split(":")[1]);
            } catch (NumberFormatException e){
                throw new StoreException(StoreErrorCode.NOT_VALID_CURSOR);
            }
        }

        // QueryDSL 이용하면 좋음!!
        Slice<Review> reviewList;
        if (idCursor != null){
            if (cursorType.equals("id")){
                if (storeId == null){
                    reviewList = reviewRepository
                            .findMyReviewsWithId(member.getMember().getId(), idCursor, pageRequest);
                } else {
                    reviewList = reviewRepository
                            .findMyReviewsWithId(member.getMember().getId(), storeId, idCursor, pageRequest);
                }
            } else {
                if (storeId == null){
                    reviewList = reviewRepository
                            .findMyReviewsWithStar(member.getMember().getId(), prevCursor, idCursor, pageRequest);
                } else {
                    reviewList = reviewRepository
                            .findMyReviewsWithStar(member.getMember().getId(), storeId, prevCursor, idCursor, pageRequest);
                }
            }
        } else {
            if (cursorType.equals("id")){
                if (storeId == null){
                    reviewList = reviewRepository
                            .findMyReviewsOrderById(member.getMember().getId(), pageRequest);
                } else {
                    reviewList = reviewRepository
                            .findMyReviewsOrderById(member.getMember().getId(), storeId, pageRequest);
                }
            } else {
                if (storeId == null){
                    reviewList = reviewRepository
                            .findMyReviewsOrderByStar(member.getMember().getId(), pageRequest);
                } else {
                    reviewList = reviewRepository
                            .findMyReviewsOrderByStar(member.getMember().getId(), storeId, pageRequest);
                }
            }
        }

        if (!reviewList.hasContent()){
            return GeneralConverter.toPagination(
                    List.of(),
                    false,
                    "-1",
                    pageSize,
                    cursorType
            );
        }

        // 다음 커서 제작
        String nextCursor;
        Review lastReview = reviewList.getContent().getLast();
        switch (cursorType){
            case "id":
                nextCursor = lastReview.getId()+":"+lastReview.getId();
                break;
            case "star":
                nextCursor = lastReview.getStar()+":"+lastReview.getId();
                break;
            default:
                nextCursor = "-1";
        }
        return GeneralConverter.toPagination(
                reviewList.getContent().stream()
                        .map(i -> {
                            if (i.getReply() != null){
                                return ReviewConverter.toGetMyReview(i, i.getMember(), i.getReply());
                            } else {
                                return ReviewConverter.toGetMyReview(i, i.getMember());
                            }
                        })
                        .toList(),
                reviewList.hasNext(),
                nextCursor,
                reviewList.getSize(),
                cursorType
        );
    }
}
