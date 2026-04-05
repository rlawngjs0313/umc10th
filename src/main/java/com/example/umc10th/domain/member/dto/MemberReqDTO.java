package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.FoodName;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.mission.enums.Address;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    // 마이페이지
    public record GetInfo(
            Long id
    ){}

    // 회원가입
    public record SignUp(
            Agree agree,
            @NotBlank(message = "이름은 빈칸일 수 없습니다.")
            String name,
            @NotNull(message = "성별은 필수입니다.")
            Gender gender,
            @NotNull(message = "생년월일은 필수입니다.")
            LocalDate birth,
            Address address,
            String detailAddress,
            List<FoodName> foodList
    ){}

    // 회원가입-약관동의
    public record Agree(
            @AssertTrue(message = "필수 동의 약관입니다.")
            Boolean age,
            @AssertTrue(message = "필수 동의 약관입니다.")
            Boolean service,
            @AssertTrue(message = "필수 동의 약관입니다.")
            Boolean privacy,
            Boolean location,
            Boolean marketing
    ){}
}
