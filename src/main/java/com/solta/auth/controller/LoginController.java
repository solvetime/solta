package com.solta.auth.controller;

import com.solta.auth.dto.AuthInfo;
import com.solta.auth.dto.LoginRequest;
import com.solta.auth.service.LoginService;
import com.solta.auth.service.RefreshTokenService;
import com.solta.global.token.CurrentUser;
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

    @GetMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request, @CurrentUser AuthInfo authInfo) {
        validateExistHeader(request);
        Long memberId = authInfo.id();
        Enumeration<String> headers = request.getHeaders("refresh-token");
        String refreshToken = extract(headers);

        refreshTokenService.matches(refreshToken, memberId);

        String accessToken = jwtTokenManager.createAccessToken(authInfo);

        return ResponseEntity.noContent()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build();
    }

    private String extract(Enumeration<String> headers) {
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith("Bearer".toLowerCase())) {
                String authValue = value.substring("Bearer".length()).trim();

                int comma = authValue.indexOf(',');
                if (comma > 0) {
                    authValue = authValue.substring(0, comma);
                }
                return authValue;
            }
        }
        return null;
    }

    private void validateExistHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshTokenHeader = request.getHeader("Refresh-Token");
        if (Objects.isNull(authorizationHeader) || Objects.isNull(refreshTokenHeader)) {
            throw new NoSuchElementException();
        }
    }
}
