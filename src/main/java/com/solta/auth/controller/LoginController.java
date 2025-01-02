package com.solta.auth.controller;

import com.solta.auth.dto.AuthInfo;
import com.solta.auth.dto.LoginRequest;
import com.solta.auth.service.LoginService;
import com.solta.auth.service.RefreshTokenService;
import com.solta.global.common.response.ApiResponse;
import com.solta.global.common.response.HttpMessage;
import com.solta.global.token.CurrentUser;
import com.solta.global.token.JwtTokenExtractor;
import com.solta.global.token.JwtTokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenManager jwtTokenManager;
    private final JwtTokenExtractor jwtTokenExtractor;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthInfo authInfo = loginService.login(loginRequest);
        String accessToken = jwtTokenManager.createAccessToken(authInfo);
        String refreshToken = jwtTokenManager.createRefreshToken();
        refreshTokenService.saveToken(refreshToken, authInfo.id());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("refresh-token", "Bearer " + refreshToken)
                .body(ApiResponse.builder()
                        .message(HttpMessage.SUCCESS.getMsg())
                        .build());
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<Object>> refresh(HttpServletRequest request, @CurrentUser AuthInfo authInfo) {
        validateExistHeader(request);
        Long memberId = authInfo.id();
        Enumeration<String> headers = request.getHeaders("refresh-token");
        String refreshToken = jwtTokenExtractor.extract(headers);

        refreshTokenService.matches(refreshToken, memberId);

        String accessToken = jwtTokenManager.createAccessToken(authInfo);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(ApiResponse.builder()
                        .message(HttpMessage.SUCCESS.getMsg())
                        .build());
    }

    private void validateExistHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshTokenHeader = request.getHeader("Refresh-Token");
        if (Objects.isNull(authorizationHeader) || Objects.isNull(refreshTokenHeader)) {
            throw new NoSuchElementException();
        }
    }
}
