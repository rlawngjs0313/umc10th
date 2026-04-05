package com.example.umc10th.domain.mission.controller.docs;

import com.example.umc10th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.res.MissionResDTO;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.dto.GeneralDTO;
import com.example.umc10th.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "미션 API")
public interface MissionControllerDocs {

    @Operation(
            summary = "미션 생성 API By 마크",
            description = """
                    # 미션 생성
                    가게 ID를 이용해 해당 가게의 미션을 생성합니다.
                    
                    ## 주의 사항
                    - 반드시 마감 기한은 프론트에서 현재시각 + a를 한 뒤 보내주시길 바랍니다.
                    
                    ## 요청 설명
                    - storeId: 가게 ID
                    - deadline: 미션 마감 기한 (현재시각 + N일)
                    - point: 미션 성공 시 지급되는 포인트
                    - conditional: 미션 성공 조건
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공 예시",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "MISSION200_1",
                                      "message": "성공적으로 미션을 생성했습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "실패 - 가게를 찾지 못한 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "STORE404_1",
                                      "message": "해당 가게가 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "실패 - 헤더에 JWT 토큰 미삽입",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "COMMON401_1",
                                      "message": "인증되지 않았습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            )
    })
    ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMission dto
    );

    // 지역 내 도전하기 전인 미션 조회
    @Operation(
            summary = "지역 내 도전하기 전인 미션 조회 API By 마크",
            description = """
                    # 지역 내 도전하기 전인 미션 조회
                    지역명을 이용하여 내가 도전하기 전인 미션들을 조회합니다.
                    커서 기반 페이지네이션으로 작동합니다
                    
                    ## 주의 사항
                    초기 검색인 경우, 커서는 반드시 -1 로 보내주시기 바랍니다.
                    
                    ## 가능한 입력
                    - address: NONE, 강남구, 동작구, 종로구, 마포구, 안암동
                    
                    ## 요청 설명
                    - address: 미션 조회할 지역명
                    - pageSize: 조회할 미션 개수
                    - cursor: 커서
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공 예시",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "MISSION200_2",
                                      "message": "성공적으로 미션을 조회했습니다.",
                                      "result": {
                                        "data": [
                                          {
                                            "missionId": 1,
                                            "point": 100,
                                            "conditional": "string"
                                          }
                                        ],
                                        "hasNext": true,
                                        "nextCursor": "1",
                                        "pageSize": 1
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "실패 - 커서값이 잘못된 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MISSION400_1",
                                      "message": "커서값이 잘못되었습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "실패 - 헤더에 JWT 토큰 미삽입",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "COMMON401_1",
                                      "message": "인증되지 않았습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            )
    })
    @Parameters(value = {
            @Parameter(
                    name = "address",
                    required = true,
                    in = ParameterIn.QUERY,
                    description = "조회할 지역명"
            ),
            @Parameter(
                    name = "pageSize",
                    required = true,
                    in = ParameterIn.QUERY,
                    description = "조회할 미션 개수"
            ),
            @Parameter(
                    name = "cursor",
                    required = true,
                    in = ParameterIn.QUERY,
                    description = "커서"
            )
    })
    ApiResponse<GeneralDTO.Pagination<MissionResDTO.GetMission>> getMissions(
            @RequestParam Address address,
            @AuthenticationPrincipal AuthMember member,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotNull(message = "커서는 빈칸일 수 없습니다. -1을 입력해주세요") String cursor
    );

    // 내가 도전중인, 도전 완료한 미션 조회
    @Operation(
            summary = "내가 도전중인, 도전 완료한 미션 조회 API By 마크",
            description = """
                    # 내가 도전중인, 도전 완료한 미션 조회
                    내가 도전중인 or 도전 완료한 미션 목록을 조회합니다.
                    커서 기반 페이지네이션으로 작동합니다
                    
                    ## 주의 사항
                    초기 검색인 경우, 커서는 반드시 -1 로 보내주시기 바랍니다.
                    
                    ## 가능한 입력
                    - isCompleted: true(완료한 미션), false(도전중인 미션)
                    
                    ## 요청 설명
                    - isCompleted: 도전 완료 여부
                    - pageSize: 조회할 미션 개수
                    - cursor: 커서
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공 예시",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "MISSION200_2",
                                      "message": "성공적으로 미션을 조회했습니다.",
                                      "result": {
                                        "data": [
                                          {
                                            "missionId": 5,
                                            "storeName": "반이학생마라탕마라반",
                                            "point": 100,
                                            "conditional": "12000원 이상 주문 시",
                                            "isCompleted": false
                                          }
                                        ],
                                        "hasNext": false,
                                        "nextCursor": "1",
                                        "pageSize": 10
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "실패 - 커서값이 잘못된 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MISSION400_1",
                                      "message": "커서값이 잘못되었습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "실패 - 헤더에 JWT 토큰 미삽입",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "COMMON401_1",
                                      "message": "인증되지 않았습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            )
    })
    @Parameters(value = {
            @Parameter(
                    name = "isCompleted",
                    required = true,
                    in = ParameterIn.QUERY,
                    description = "완료 여부"
            ),
            @Parameter(
                    name = "pageSize",
                    required = true,
                    in = ParameterIn.QUERY,
                    description = "조회할 미션 개수"
            ),
            @Parameter(
                    name = "cursor",
                    required = true,
                    in = ParameterIn.QUERY,
                    description = "커서"
            )
    })
    ApiResponse<GeneralDTO.Pagination<MissionResDTO.GetMyMission>> getMyMissions(
            @AuthenticationPrincipal AuthMember member,
            @RequestParam Boolean isCompleted,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotNull(message = "커서는 빈칸일 수 없습니다. -1을 입력해주세요") String cursor
    );

    // 미션 도전 처리
    @Operation(
            summary = "미션 도전 처리 API By 마크",
            description = """
                    # 미션 도전 처리
                    아직 도전하지 않은 미션을 도전 처리합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공 예시",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "MISSION200_3",
                                      "message": "성공적으로 미션을 도전처리했습니다.",
                                      "result": {
                                        "missionId": 3,
                                        "point": 100,
                                        "conditional": "12000원 이상 주문 시"
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "실패 - 미션을 찾지 못한 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MISSION404_1",
                                      "message": "해당 미션을 찾지 못했습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "실패 - 이미 해당 미션을 도전하고 있는 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MISSION400_2",
                                      "message": "이미 도전중인 미션입니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "실패 - 헤더에 JWT 토큰 미삽입",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "COMMON401_1",
                                      "message": "인증되지 않았습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            )
    })
    @Parameter(
            name = "missionId",
            required = true,
            in = ParameterIn.PATH,
            description = "도전 처리할 미션 ID"
    )
    ApiResponse<MissionResDTO.TryMission> tryMission(
            @PathVariable Long missionId,
            @AuthenticationPrincipal AuthMember member
    );

    // 미션 완료 요청 보내기 (단순 전환)
    @Operation(
            summary = "미션 완료 요청 보내기 (단순 전환) API By 마크",
            description = """
                    # 미션 완료 요청 보내기 (단순 전환)
                    도전 중인 미션을 완료처리 합니다.
                    
                    ## 주의 사항
                    해당 API는 단순히 데이터를 전환합니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공 예시",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "MISSION200_4",
                                      "message": "성공적으로 미션을 완료헀습니다.",
                                      "result": {
                                        "missionId": 5,
                                        "completedAt": "2026-04-05T20:12:29.985785",
                                        "managerNumber": 920394810
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "실패 - 해당 미션을 찾지 못한 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MISSION404_1",
                                      "message": "해당 미션을 찾지 못했습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "실패 - 해당 미션이 도전중이지 않은 상태인 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MISSION400_3",
                                      "message": "도전중인 미션이 아닙니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "실패 - 헤더에 JWT 토큰 미삽입",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "COMMON401_1",
                                      "message": "인증되지 않았습니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            )
    })
    ApiResponse<MissionResDTO.CompleteMission> completeMission(
            @PathVariable Long missionId,
            @AuthenticationPrincipal AuthMember member
    );
}
