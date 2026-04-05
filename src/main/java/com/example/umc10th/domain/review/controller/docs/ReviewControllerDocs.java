package com.example.umc10th.domain.review.controller.docs;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
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
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "리뷰 API")
public interface ReviewControllerDocs {

    // 리뷰 생성
    @Operation(
            summary = "리뷰 생성 API By 마크",
            description = """
                    # 리뷰 생성
                    해당 가게에 리뷰를 생성합니다
                    
                    ## 가능한 요청
                    - star: 1.0 ~ 5.0
                    
                    ## 요청 설명
                    - content: 리뷰 내용
                    - star: 별점
                    - storeId: 가게 ID
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
                                      "code": "REVIEW201_1",
                                      "message": "성공적으로 리뷰를 생성했습니다.",
                                      "result": {
                                        "reviewId": 8,
                                        "createdAt": "2026-04-05T20:28:05.146336"
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "실패 - 해당 가게를 찾지 못한 경우",
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
                    responseCode = "400",
                    description = "실패 - 검증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "COMMON400_1",
                                      "message": "잘못된 요청입니다.",
                                      "result": {
                                        "star": "별점은 1점 이상 5점 이하여야합니다."
                                      }
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
    ApiResponse<ReviewResDTO.CreateReview> createReview(
            @AuthenticationPrincipal AuthMember member,
            @RequestBody @Valid ReviewReqDTO.CreateReview dto
    );

    // 리뷰 목록 조회
    @Operation(
            summary = "리뷰 목록 조회 API By 마크",
            description = """
                    # 리뷰 목록 조회
                    해당 가게에 리뷰 목록을 조회합니다.
                    커서 기반 페이지네이션으로 작동합니다
                    
                    ## 요청 설명
                    - storeId: 리뷰를 조회할 가게 ID
                    - pageSize: 조회할 리뷰 개수
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
                                      "code": "REVIEW200_1",
                                      "message": "성공적으로 리뷰를 읽었습니다.",
                                      "result": {
                                        "data": [
                                          {
                                            "reviewId": 8,
                                            "createdAt": "2026-04-05T20:28:05.123403",
                                            "memberName": "string",
                                            "star": 5,
                                            "content": "string"
                                          },
                                          {
                                            "reviewId": 7,
                                            "createdAt": "2026-04-02T18:38:11.164609",
                                            "memberName": "string",
                                            "star": 4,
                                            "content": "string5"
                                          }
                                        ],
                                        "hasNext": true,
                                        "nextCursor": "7",
                                        "pageSize": 2
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "실패 - 해당 가게를 찾지 못한 경우",
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
                    responseCode = "400",
                    description = "실패 - 커서값이 잘못된 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "REVIEW400_1",
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
                    name = "storeId",
                    required = true,
                    in = ParameterIn.QUERY,
                    description = "가게 ID"
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
    ApiResponse<GeneralDTO.Pagination<ReviewResDTO.GetReview>> getReviews(
            @RequestParam Long storeId,
            @RequestParam(defaultValue = "10") @Min(value = 0, message = "조회할 페이지 수는 음수일 수 없습니다.")
            Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotBlank(message = "커서값이 빈칸일 수 없습니다.")
            String cursor
    );

    // 내가 작성한 리뷰 목록 조회하기
    @Operation(
            summary = "내가 작성한 리뷰 목록 조회하기 API By 마크",
            description = """
                    # 내가 작성한 리뷰 목록 조회하기
                    해당 가게에 내가 작성한 리뷰 목록을 조회합니다.
                    커서 기반 페이지네이션으로 작동합니다
                    
                    ## 가능한 입력
                    - cursorType: id, star (대소문자 구분 X)
                    
                    ## 요청 설명
                    - storeId: 리뷰를 조회할 가게 ID
                    - pageSize: 조회할 리뷰 개수
                    - cursor: 커서
                    - cursorType: 커서 타입
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
                                      "code": "REVIEW200_1",
                                      "message": "성공적으로 리뷰를 읽었습니다.",
                                      "result": {
                                        "data": [
                                          {
                                            "reviewId": 8,
                                            "authorName": "string",
                                            "star": 5,
                                            "createdAt": "2026-04-05T20:28:05.123403",
                                            "content": "string",
                                            "reply": null
                                          },
                                          {
                                            "reviewId": 7,
                                            "authorName": "string",
                                            "star": 4,
                                            "createdAt": "2026-04-02T18:38:11.164609",
                                            "content": "string5",
                                            "reply": null
                                          }
                                        ],
                                        "hasNext": true,
                                        "nextCursor": "7:7",
                                        "pageSize": 2
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "실패 - 해당 가게를 찾지 못한 경우",
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
                    responseCode = "400",
                    description = "실패 - 커서값이 잘못된 경우",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "REVIEW400_1",
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
                    name = "storeId",
                    required = true,
                    in = ParameterIn.QUERY,
                    description = "가게 ID"
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
    ApiResponse<GeneralDTO.Pagination<ReviewResDTO.GetMyReview>> getMyReview(
            @AuthenticationPrincipal AuthMember member,
            @RequestParam(required = false) @Nullable Long storeId,
            @RequestParam(defaultValue = "10") @Min(value = 0, message = "조회할 페이지 수는 음수일 수 없습니다.")
            Integer pageSize,
            @RequestParam(defaultValue = "-1") @NotBlank(message = "커서값은 빈칸일 수 없습니다.")
            String cursor,
            @RequestParam(defaultValue = "id") @NotBlank(message = "커서종류는 빈칸일 수 없습니다. (id, star)")
            String cursorType
    );
}
