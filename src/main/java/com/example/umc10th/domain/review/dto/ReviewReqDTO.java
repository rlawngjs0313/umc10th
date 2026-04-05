package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ReviewReqDTO {

    // 리뷰 생성
    public record CreateReview(
            @NotBlank(message = "리뷰 내용은 빈칸일 수 없습니다.")
            String content,
            @Min(value = 1, message = "별점은 1점 이상 5점 이하여야합니다.")
            @Max(value = 5, message = "별점은 1점 이상 5점 이하여야합니다.")
            BigDecimal star,
            @NotNull(message = "가게 ID는 필수입니다.")
            Long storeId
    ) {}
}
