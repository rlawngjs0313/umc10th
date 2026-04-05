package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.Term;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.global.security.dto.OAuthDTO;

import java.time.LocalDateTime;

public class MemberConverter {

    // 마이페이지
    public static MemberResDTO.GetInfo toGetInfo(
            Member member
    ){
        return MemberResDTO.GetInfo.builder()
                .email(member.getEmail())
                .name(member.getName())
                .point(member.getPoint())
                .phoneNumber(member.getPhoneNumber())
                .profileUrl(member.getProfileUrl())
                .build();
    }

    // 회원가입(Member)
    public static Member toMember(
            OAuthDTO dto
    ){
        return Member.builder()
                .socialType(dto.getSocialType())
                .socialUid(dto.getSocialUid())
                .email(dto.getSocialEmail())
                .name(dto.getName())
                .build();
    }

    // 회원가입(MemberTerm)
    public static MemberTerm toMemberTerm(
            Member member,
            Term term
    ){
        return MemberTerm.builder()
                .member(member)
                .term(term)
                .build();
    }

    // 회원가입(MemberFood)
    public static MemberFood toMemberFood(
            Member member,
            Food food
    ){
        return MemberFood.builder()
                .member(member)
                .food(food)
                .build();
    }

    // 회원가입
    public static MemberResDTO.SignUp toSignUp(
            Member member
    ){
        return MemberResDTO.SignUp.builder()
                .createdAt(LocalDateTime.now())
                .id(member.getId())
                .build();
    }

    // 홈화면
    public static MemberResDTO.Home toHome(
            Long missionCnt,
            Member member
    ){
        return MemberResDTO.Home.builder()
                .missionCnt(missionCnt)
                .point(member.getPoint())
                .build();
    }

    // 로그인
    public static MemberResDTO.Login toLogin(
            String accessToken
    ){
        return MemberResDTO.Login.builder()
                .accessToken(accessToken)
                .build();
    }
}
