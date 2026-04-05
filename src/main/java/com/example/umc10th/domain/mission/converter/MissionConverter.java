package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.res.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;

import java.time.LocalDateTime;

public class MissionConverter {

    // 가게 미션 생성
    public static Mission toMission(
            Store store,
            MissionReqDTO.CreateMission dto
    ) {
        return Mission.builder()
                .store(store)
                .conditional(dto.conditional())
                .point(dto.point())
                .deadline(dto.deadline())
                .build();
    }

    // 가게 내 미션 조회
    public static MissionResDTO.GetMission toGetMission(
            Mission mission
    ) {
        return MissionResDTO.GetMission.builder()
                .conditional(mission.getConditional())
                .point(mission.getPoint())
                .missionId(mission.getId())
                .build();
    }

    // 가게 완료 처리
    public static MissionResDTO.CompleteMission toCompleteMission(
            Mission mission
    ){
        return MissionResDTO.CompleteMission.builder()
                .completedAt(LocalDateTime.now())
                .managerNumber(mission.getStore().getManagerNumber())
                .missionId(mission.getId())
                .build();
    }
}
