package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.res.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;

public class MemberMissionConverter {

    // 내가 도전중인, 완료한 미션 조회
    public static MissionResDTO.GetMyMission toGetMyMission(
            MemberMission memberMission
    ){
        return MissionResDTO.GetMyMission.builder()
                .conditional(memberMission.getMission().getConditional())
                .isCompleted(memberMission.getIsComplete())
                .missionId(memberMission.getMission().getId())
                .point(memberMission.getMission().getPoint())
                .storeName(memberMission.getMission().getStore().getName())
                .build();
    }

    // 사용자미션 엔티티 생성
    public static MemberMission toMemberMission(
            Member member,
            Mission mission
    ){
        return MemberMission.builder()
                .isComplete(false)
                .member(member)
                .mission(mission)
                .build();
    }

    // 미션 도전하기
    public static MissionResDTO.TryMission toTryMission(
            Mission mission
    ){
        return MissionResDTO.TryMission.builder()
                .conditional(mission.getConditional())
                .missionId(mission.getId())
                .point(mission.getPoint())
                .build();
    }
}
