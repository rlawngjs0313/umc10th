package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    NOT_VALID_CURSOR(HttpStatus.BAD_REQUEST,
            "MISSION400_1",
            "커서값이 잘못되었습니다."),
    ALREADY_TRY(HttpStatus.BAD_REQUEST,
            "MISSION400_2",
            "이미 도전중인 미션입니다."),
    NOT_TRY(HttpStatus.BAD_REQUEST,
            "MISSION400_3",
            "도전중인 미션이 아닙니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,
            "MISSION404_1",
            "해당 미션을 찾지 못했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
