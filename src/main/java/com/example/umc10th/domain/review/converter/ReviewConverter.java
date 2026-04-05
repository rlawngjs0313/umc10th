package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Reply;
import com.example.umc10th.domain.review.entity.Review;

import java.time.LocalDateTime;

public class ReviewConverter {

    // 리뷰 엔티티
    public static Review toReview(
            Store store,
            Member member,
            ReviewReqDTO.CreateReview dto
    ){
        return Review.builder()
                .store(store)
                .content(dto.content())
                .star(dto.star())
                .member(member)
                .build();
    }

    // 리뷰 생성
    public static ReviewResDTO.CreateReview toCreateReview(
            Review review
    ){
        return ReviewResDTO.CreateReview.builder()
                .createdAt(LocalDateTime.now())
                .reviewId(review.getId())
                .build();
    }

    // 리뷰 조회
    public static ReviewResDTO.GetReview toGetReview(
            Review review
    ){
        return ReviewResDTO.GetReview.builder()
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .memberName(review.getMember().getName())
                .reviewId(review.getId())
                .star(review.getStar())
                .build();
    }

    // 내가 작성한 리뷰 조회
    public static ReviewResDTO.GetMyReview toGetMyReview(
            Review review,
            Member member,
            Reply reply
    ){
        return ReviewResDTO.GetMyReview.builder()
                .content(review.getContent())
                .reply(toManagerReply(reply))
                .authorName(member.getName())
                .createdAt(review.getCreatedAt())
                .reviewId(review.getId())
                .star(review.getStar())
                .build();
    }

    // 내가 작성한 리뷰 조회
    public static ReviewResDTO.GetMyReview toGetMyReview(
            Review review,
            Member member
    ){
        return ReviewResDTO.GetMyReview.builder()
                .content(review.getContent())
                .reply(null)
                .authorName(member.getName())
                .createdAt(review.getCreatedAt())
                .reviewId(review.getId())
                .star(review.getStar())
                .build();
    }

    // 내가 작성한 리뷰 조회 (사장님 답글)
    public static ReviewResDTO.ManagerReply toManagerReply(
            Reply reply
    ){
        return ReviewResDTO.ManagerReply.builder()
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
