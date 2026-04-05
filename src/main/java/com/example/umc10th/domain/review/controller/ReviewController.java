package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.controller.docs.ReviewControllerDocs;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.dto.GeneralDTO;
import com.example.umc10th.global.security.entity.AuthMember;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class ReviewController implements ReviewControllerDocs {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/v1/reviews")
    public ApiResponse<ReviewResDTO.CreateReview> createReview(
            @AuthenticationPrincipal AuthMember member,
            @RequestBody @Valid ReviewReqDTO.CreateReview dto
    ){
        BaseSuccessCode code = ReviewSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, reviewService.createReview(member, dto));
    }

    // 리뷰 목록 조회
    @GetMapping("/v1/reviews")
    public ApiResponse<GeneralDTO.Pagination<ReviewResDTO.GetReview>> getReviews(
            @RequestParam Long storeId,
            @RequestParam(defaultValue = "10") @Min(value = 0, message = "조회할 페이지 수는 음수일 수 없습니다.")
            Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotBlank(message = "커서값이 빈칸일 수 없습니다.")
            String cursor
    ){
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.getReviews(storeId, pageSize, cursor));
    }

    // 내가 작성한 리뷰 목록 조회하기
    @GetMapping("/v1/reviews/me")
    public ApiResponse<GeneralDTO.Pagination<ReviewResDTO.GetMyReview>> getMyReview(
            @AuthenticationPrincipal AuthMember member,
            @RequestParam(required = false) @Nullable Long storeId,
            @RequestParam(defaultValue = "10") @Min(value = 0, message = "조회할 페이지 수는 음수일 수 없습니다.")
            Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotBlank(message = "커서값은 빈칸일 수 없습니다.")
            String cursor,
            @RequestParam(defaultValue = "id") @NotBlank(message = "커서종류는 빈칸일 수 없습니다. (id, star)")
            String cursorType
    ){
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.getMyReview(member, storeId, pageSize, cursor, cursorType));
    }
}
