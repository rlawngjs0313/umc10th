package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.controller.docs.StoreControllerDocs;
import com.example.umc10th.domain.mission.dto.res.StoreResDTO;
import com.example.umc10th.domain.mission.exception.code.StoreSuccessCode;
import com.example.umc10th.domain.mission.service.StoreService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.dto.GeneralDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class StoreController implements StoreControllerDocs {

    private final StoreService storeService;

    // 가게 정보 조회
    @GetMapping("/v1/stores/search")
    public ApiResponse<GeneralDTO.Pagination<StoreResDTO.GetStore>> getStore(
            @RequestParam String query,
            @RequestParam(defaultValue = "10") @Min(value = 0, message = "조회할 페이지는 음수면 안됩니다.") Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotNull(message = "커서는 빈칸일 수 없습니다. -1을 입력해주세요") String cursor
    ){
        BaseSuccessCode code = StoreSuccessCode.OK;
        return ApiResponse.onSuccess(code, storeService.getStore(query, pageSize, cursor));
    }
}
