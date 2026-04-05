package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.controller.docs.AuthControllerDocs;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.security.entity.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/v1/sign-up")
    public ApiResponse<MemberResDTO.SignUp> signup(
            @AuthenticationPrincipal AuthMember member,
            @RequestBody @Valid MemberReqDTO.SignUp dto
    ){
        if (member == null) throw new MemberException(MemberErrorCode.NOT_REGISTER);
        BaseSuccessCode code = MemberSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, memberService.signup(member, dto));
    }
}
