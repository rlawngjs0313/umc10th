package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK,
            "STORE200_1",
            "성공적으로 가게를 조회했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
