package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.controller.docs.MemberControllerDocs;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    // 마이페이지
    @GetMapping("/v2/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @AuthenticationPrincipal AuthMember member
    ){
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getInfo(member));
    }

    // 홈
    @GetMapping("/v1/home")
    public ApiResponse<MemberResDTO.Home> home(
            @AuthenticationPrincipal AuthMember member
    ){
        BaseSuccessCode code = MemberSuccessCode.HOME;
        return ApiResponse.onSuccess(code, memberService.home(member));
    }
}
