package com.example.umc10th.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {

    // 마이페이지
    @Builder
    public record GetInfo(
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            Integer point
    ){}

    // 회원가입
    @Builder
    public record SignUp(
            Long id,
            LocalDateTime createdAt
    ){}

    // 홈화면
    @Builder
    public record Home(
            Long missionCnt,
            Integer point
    ){}

    // 로그인
    @Builder
    public record Login(
            String accessToken
    ){}
}
