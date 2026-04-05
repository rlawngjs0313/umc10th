package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.Term;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.enums.TermName;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.*;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberTermRepository memberTermRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final TermRepository termRepository;
    private final FoodRepository foodRepository;
    private final MemberMissionRepository memberMissionRepository;

    // 마이페이지
    public MemberResDTO.GetInfo getInfo(
            AuthMember member
    ) {
        // 컨버터를 이용해서 응답 DTO 생성 & return
        return MemberConverter.toGetInfo(member.getMember());
    }

    // 회원가입
    @Transactional
    public MemberResDTO.SignUp signup(
            AuthMember authMember,
            MemberReqDTO.SignUp dto
    ) {

        // 약관 엔티티 생성
        List<Term> terms = new ArrayList<>();
        if (dto.agree().age()) terms.add(termRepository.findByName(TermName.AGE)
                .orElseGet(() -> {
                    Term term = Term.builder().name(TermName.AGE).build();
                    termRepository.save(term);
                    return term;
                }));
        if (dto.agree().privacy()) terms.add(termRepository.findByName(TermName.PRIVACY)
                .orElseGet(() -> {
                    Term term = Term.builder().name(TermName.PRIVACY).build();
                    termRepository.save(term);
                    return term;
                }));
        if (dto.agree().service()) terms.add(termRepository.findByName(TermName.SERVICE)
                .orElseGet(() -> {
                    Term term = Term.builder().name(TermName.SERVICE).build();
                    termRepository.save(term);
                    return term;
                }));
        if (dto.agree().marketing()) terms.add(termRepository.findByName(TermName.MARKETING)
                .orElseGet(() -> {
                    Term term = Term.builder().name(TermName.MARKETING).build();
                    termRepository.save(term);
                    return term;
                }));
        if (dto.agree().location()) terms.add(termRepository.findByName(TermName.LOCATION)
                .orElseGet(() -> {
                    Term term = Term.builder().name(TermName.LOCATION).build();
                    termRepository.save(term);
                    return term;
                }));

        // 음식 엔티티 생성
        List<Food> foods = dto.foodList().stream()
                .map(i -> foodRepository.findByName(i)
                        .orElseGet(() -> {
                            Food food = Food.builder().name(i).build();
                            foodRepository.save(food);
                            return food;
                        }))
                .toList();

        Member member = memberRepository.findById(authMember.getMember().getId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 사용자-약관 연관관계 설정
        List<MemberTerm> memberTerms =
                terms.stream()
                        .map(i -> MemberConverter.toMemberTerm(member, i))
                        .toList();

        // 사용자-선호 음식 연관관계 설정
        List<MemberFood> memberFoods =
                foods.stream()
                        .map(i -> MemberConverter.toMemberFood(member, i))
                        .toList();

        // DB 적용
        member.signUp(dto);
        memberFoodRepository.saveAll(memberFoods);
        memberTermRepository.saveAll(memberTerms);
        return MemberConverter.toSignUp(member);
    }

    // 홈화면
    public MemberResDTO.Home home(
            AuthMember authMember
    ) {

        // 완료한 미션 수
        Long missionCnt = memberMissionRepository.countByMemberAndIsCompleteIsTrue(authMember.getMember());

        return MemberConverter.toHome(missionCnt, authMember.getMember());
    }
}
