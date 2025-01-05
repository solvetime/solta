package com.solta.global.token;

import java.util.Enumeration;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenExtractor {

    public String extract(Enumeration<String> headers) {
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

    public String extract(String value) {
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
