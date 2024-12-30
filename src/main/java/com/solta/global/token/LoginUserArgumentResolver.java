package com.solta.global.token;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenManager jwtTokenManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Authorization");
        if (token == null) {
            throw new NoSuchElementException();
        }

        return jwtTokenManager.getParsedClaims(extract(token));
    }

    private String extract(String value) {
        if (value.toLowerCase().startsWith("Bearer".toLowerCase())) {
            String authValue = value.substring("Bearer".length()).trim();

            int comma = authValue.indexOf(',');
            if (comma > 0) {
                authValue = authValue.substring(0, comma);
            }
            return authValue;
        }
        return null;
    }
}
