package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted_at = CURRENT_TIMESTAMP WHERE member_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.NONE;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "address", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Address address = Address.NONE;

    @Column(name = "member_detail_address", nullable = false)
    @Builder.Default
    private String detailAddress = "";

    @Column(name = "social_uid", nullable = false)
    @Builder.Default
    private String socialUid = "";

    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SocialType socialType = SocialType.LOCAL;

    @Column(name = "profile_url", nullable = false)
    @Builder.Default
    private String profileUrl = "";

    @Column(name = "member_point", nullable = false)
    @Builder.Default
    private Integer point = 0;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    public void signUp(
            MemberReqDTO.SignUp dto
    ){
        this.name = dto.name();
        this.gender = dto.gender();
        this.birth = dto.birth();
        this.address = dto.address();
        this.detailAddress = dto.detailAddress();
    }

    public void updatePoint(Integer point) {
        this.point += point;
    }
}
