package com.example.umc10th.domain.member.controller.docs;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "마이페이지 API")
public interface MemberControllerDocs {

    // 마이페이지
    @Operation(
            summary = "마이페이지 API By 마크",
            description = """
                    # 마이페이지
                    
                    ## 요청 설명
                    - 헤더: Authorize: Bearer {JWT 토큰}
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
                                      "code": "MEMBER200_1",
                                      "message": "성공적으로 유저를 조회했습니다.",
                                      "result": {
                                        "name": "마크",
                                        "profileUrl": "https://~~",
                                        "email": "example@example.com",
                                        "phoneNumber": "010-XXXX-XXXX",
                                        "point": 0
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
    ApiResponse<MemberResDTO.GetInfo> getInfo(
            @AuthenticationPrincipal AuthMember member
    );

    @Operation(
            summary = "홈화면 API By 마크",
            description = """
                    # 홈화면
                    
                    ## 요청 설명
                    - 헤더: Authorize: Bearer {JWT 토큰}
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
                                      "code": "MEMBER200_1",
                                      "message": "성공적으로 홈화면을 조회했습니다.",
                                      "result": {
                                        "missionCnt": 0,
                                        "point": 0
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
    ApiResponse<MemberResDTO.Home> home(
            @AuthenticationPrincipal AuthMember member
    );
}
