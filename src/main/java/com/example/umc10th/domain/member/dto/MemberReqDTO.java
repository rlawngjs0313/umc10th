package com.example.umc10th.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberReqDTO {

    // Request Body 예시
    public record RequestBody(
            String stringTest,
            Long longTest
    ){}

    // public static class
    @Getter
    public static class RequestBodyClass{
        private String stringTest;
        private Long longTest;
    }
}
