package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    Slice<MemberMission> findAllByIsCompleteAndMember(Boolean isComplete, Member member, Pageable pageable);

    Slice<MemberMission> findAllByIsCompleteAndMemberAndIdLessThan(Boolean isComplete, Member member, Long idIsLessThan, Pageable pageable);


    boolean existsByMissionAndMember(Mission mission, Member member);

    Optional<MemberMission> findByMemberAndMission(Member member, Mission mission);

    Long countByMemberAndIsCompleteIsTrue(Member member);
}
