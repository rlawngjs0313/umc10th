package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.controller.docs.MissionControllerDocs;
import com.example.umc10th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.res.MissionResDTO;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.dto.GeneralDTO;
import com.example.umc10th.global.security.entity.AuthMember;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class MissionController implements MissionControllerDocs {

    private final MissionService missionService;

    // 가게 미션 생성
    @PostMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMission dto
    ){
        BaseSuccessCode code = MissionSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
    }

    // 지역 내 도전하기 전인 미션 조회
    @GetMapping("/v1/missions")
    public ApiResponse<GeneralDTO.Pagination<MissionResDTO.GetMission>> getMissions(
            @RequestParam Address address,
            @AuthenticationPrincipal AuthMember member,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotNull(message = "커서는 빈칸일 수 없습니다. -1을 입력해주세요") String cursor
    ){
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(address, member, pageSize, cursor));
    }

    // 내가 도전중인, 도전 완료한 미션 조회
    @GetMapping("/v1/missions/me")
    public ApiResponse<GeneralDTO.Pagination<MissionResDTO.GetMyMission>> getMyMissions(
            @AuthenticationPrincipal AuthMember member,
            @RequestParam Boolean isCompleted,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotNull(message = "커서는 빈칸일 수 없습니다. -1을 입력해주세요") String cursor
    ){
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMyMissions(member, isCompleted, pageSize, cursor));
    }

    // 미션 도전 처리
    @PostMapping("/v1/missions/{missionId}/try")
    public ApiResponse<MissionResDTO.TryMission> tryMission(
            @PathVariable Long missionId,
            @AuthenticationPrincipal AuthMember member
    ){
        BaseSuccessCode code = MissionSuccessCode.TRY;
        return ApiResponse.onSuccess(code, missionService.tryMission(missionId, member));
    }

    // 미션 완료 요청 보내기 (단순 전환)
    @PostMapping("/v1/missions/{missionId}/complete")
    public ApiResponse<MissionResDTO.CompleteMission> completeMission(
            @PathVariable Long missionId,
            @AuthenticationPrincipal AuthMember member
    ){
        BaseSuccessCode code = MissionSuccessCode.COMPLETE;
        return ApiResponse.onSuccess(code, missionService.completeMission(missionId, member));
    }
}
