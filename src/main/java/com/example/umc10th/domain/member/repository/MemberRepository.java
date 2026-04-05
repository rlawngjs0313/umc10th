package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT * " +
            "FROM member AS m " +
            "WHERE m.member_name = :name AND m.deleted_at IS NULL", nativeQuery = true)
    Optional<Member> findActiveMember(String name);

    Optional<Member> findByEmail(String email);

    Optional<Member> findBySocialTypeAndSocialUid(SocialType socialType, String socialUid);

    Optional<Member> findBySocialUid(String socialUid);
}
