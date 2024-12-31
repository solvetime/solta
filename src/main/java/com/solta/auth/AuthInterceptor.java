package com.solta.auth;

import com.solta.global.token.JwtTokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenManager jwtTokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Enumeration<String> headers = request.getHeaders(HttpHeaders.AUTHORIZATION);
        String token = extract(headers);

        if (jwtTokenManager.isValid(token)) {
            return true;
        }

        log.info("no token" + request.getRequestURI());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
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
}
