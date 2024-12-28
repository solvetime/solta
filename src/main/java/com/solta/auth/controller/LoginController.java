package com.solta.auth.controller;

import com.solta.auth.dto.AuthInfo;
import com.solta.auth.dto.LoginRequest;
import com.solta.auth.service.LoginService;
import com.solta.auth.service.RefreshTokenService;
import com.solta.global.token.JwtTokenManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthInfo authInfo = loginService.login(loginRequest);
        String accessToken = jwtTokenManager.createAccessToken(authInfo);
        String refreshToken = jwtTokenManager.createRefreshToken();
        refreshTokenService.saveToken(refreshToken, authInfo.id());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("refresh-token", "Bearer " + refreshToken)
                .build();
    }
}
