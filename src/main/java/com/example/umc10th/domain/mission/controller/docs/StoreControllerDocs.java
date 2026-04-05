package com.example.umc10th.domain.mission.controller.docs;

import com.example.umc10th.domain.mission.dto.res.StoreResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.dto.GeneralDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "가게 API")
public interface StoreControllerDocs {

    @Operation(
            summary = "가게 정보 조회 API By 마크",
            description = """
                    # 가게 정보 조회
                    가게 정보를 커서페이지네이션 방식으로 조회합니다.
                    
                    ## 주의 사항
                    - 조회할 데이터 개수(pageSize)는 양수여야 함
                    - 커서(cursor)는 기본값이 -1, 빈칸이거나 누락 금지
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
                                      "code": "STORE200_1",
                                      "message": "성공적으로 가게를 조회했습니다.",
                                      "result": {
                                        "data": [
                                          {
                                            "storeId": 1,
                                            "storeName": "반이학생마라탕마라반"
                                          }
                                        ],
                                        "hasNext": false,
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
                                      "code": "STORE400_1",
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
                    name = "query",
                    required = true,
                    description = """
                            검색할 가게 이름
                            """,
                    in = ParameterIn.QUERY,
                    example = "반이"
            ),
            @Parameter(
                    name = "pageSize",
                    required = true,
                    description = """
                            조회할 데이터의 개수
                            """,
                    in = ParameterIn.QUERY,
                    example = "10"
            ),
            @Parameter(
                    name = "cursor",
                    required = true,
                    description = """
                            커서 페이지네이션용 커서
                            """,
                    in = ParameterIn.QUERY,
                    example = "-1 | 285:285"
            ),
    })
    ApiResponse<GeneralDTO.Pagination<StoreResDTO.GetStore>> getStore(
            @RequestParam String query,
            @RequestParam(defaultValue = "10") @Min(value = 0, message = "조회할 페이지는 음수면 안됩니다.") Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotNull(message = "커서는 빈칸일 수 없습니다. -1을 입력해주세요") String cursor
    );
}
