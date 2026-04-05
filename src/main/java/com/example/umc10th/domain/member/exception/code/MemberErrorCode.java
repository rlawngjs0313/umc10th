package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MEMBER404_1",
            "해당 사용자를 찾을 수 없습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST,
            "MEMBER400_1",
            "아이디 혹은 비밀번호가 틀렸습니다."),
    NOT_SUPPORT_SOCIAL_PROVIDER(HttpStatus.BAD_REQUEST,
            "MEMBER400_2",
            "지원하지 않는 소셜 로그인입니다."),
    NOT_REGISTER_PASSKEY(HttpStatus.BAD_REQUEST,
            "MEMBER400_3",
            "해당 유저는 패스키를 등록하지 않았습니다."),
    NOT_REGISTER(HttpStatus.BAD_REQUEST,
            "MEMBER400_4",
            "아직 소셜로그인을 하지 않은 상태입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
