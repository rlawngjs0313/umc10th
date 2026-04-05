package com.example.umc10th.domain.mission.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

public class MissionResDTO {

    // 가게 내 미션 조회
    @Builder
    public record GetMission(
        Long missionId,
        Integer point,
        String conditional
    ){}

    // 내가 도전하는, 완료한 미션 조회하기
    @Builder
    public record GetMyMission(
            Long missionId,
            String storeName,
            Integer point,
            String conditional,
            Boolean isCompleted
    ){}

    // 미션 도전하기
    @Builder
    public record TryMission(
            Long missionId,
            Integer point,
            String conditional
    ){}

    // 미션 완료 요청 보내기
    @Builder
    public record CompleteMission(
            Long missionId,
            LocalDateTime completedAt,
            Long managerNumber
    ){}
}
