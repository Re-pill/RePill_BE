package com.repill.backend.global.security.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.global.security.authDTO.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "카카오 로그인 API", description = "카카오 로그인 및 회원 가입을 진행하는 API입니다. 인가코드를 넘겨주세요")
    @GetMapping("/login/kakao")
    public ApiResponse<AuthResponseDTO.OAuthResponse> kakaoLogin(@RequestParam("code") String code) {
        return ApiResponse.of(SuccessStatus.USER_LOGIN_OK, authService.kakaoLogin(code));
    } //카카오 로그인
}