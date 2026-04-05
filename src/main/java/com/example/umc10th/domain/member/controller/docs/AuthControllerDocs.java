package com.example.umc10th.domain.member.controller.docs;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "인증 API")
public interface AuthControllerDocs {

    @Operation(
            summary = "회원가입 API By 마크",
            description = """
                    # 회원가입
                    회원가입을 진행합니다. 반드시 `/oauth/authorize/**` 를 입력해
                    소셜 로그인을 마친 뒤 호출해주세요.
                    
                    ## 가능한 소셜 로그인 종류
                    - kakao: /oauth/authorize/kakao
                    
                    ## 요청 설명
                    - agree: 서비스 이용 동의
                        - age: 만 14세 이상 (필수)
                        - service: 서비스 이용약관 (필수)
                        - privacy: 개인 정보 처리 방침 (필수)
                        - location: 위치정보 제공 (선택)
                        - marketing: 마케팅 수신 동의 (선택)
                    - name: 이름 (필수)
                    - gender: 성별 (필수)
                    - birth: 생년월일 (필수)
                    - address: 주소 (선택)
                    - detailAddress: 상세주소 (선택)
                    - foodList: 선호 음식 종류 (선택)
                    
                    ## 각 파라미터별 가능한 입력
                    - gender: MALE, FEMALE, NONE
                    - address: NONE, 강남구, 동작구, 종로구, 마포구, 안암동
                    - foodList: NONE, KOREAN, JAPANESE, CHINESE
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
                                      "code": "MEMBER201_1",
                                      "message": "성공적으로 유저를 생성했습니다.",
                                      "result": {
                                        "id": 1,
                                        "createdAt": "2026-04-04T01:53:52.701788"
                                      }
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "실패 - 헤더에 JWT 토큰 미삽입 (소셜 로그인 미진행)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MEMBER400_4",
                                      "message": "아직 소셜로그인을 하지 않은 상태입니다.",
                                      "result": null
                                    }
                                    """
                            )
                    )
            )
    })
    ApiResponse<MemberResDTO.SignUp> signup(
            @AuthenticationPrincipal AuthMember member,
            @RequestBody @Valid MemberReqDTO.SignUp dto
    );
}
