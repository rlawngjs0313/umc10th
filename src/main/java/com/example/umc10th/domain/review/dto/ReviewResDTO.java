package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReviewResDTO {

    // 리뷰 생성
    @Builder
    public record CreateReview(
            Long reviewId,
            LocalDateTime createdAt
    ) {}

    // 리뷰 조회
    @Builder
    public record GetReview(
            Long reviewId,
            LocalDateTime createdAt,
            String memberName,
            BigDecimal star,
            String content
    ){}

    // 내가 작성한 리뷰 조회
    @Builder
    public record GetMyReview(
            Long reviewId,
            String authorName,
            BigDecimal star,
            LocalDateTime createdAt,
            String content,
            ManagerReply reply
    ){}

    // 내가 작성한 리뷰 조회 (사장님 답글)
    @Builder
    public record ManagerReply(
            String content,
            LocalDateTime createdAt
    ){}
}
