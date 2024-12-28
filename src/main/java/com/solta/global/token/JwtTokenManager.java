package com.solta.global.token;

import com.solta.auth.dto.AuthInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenManager {

    private final Key signingKey;
    private final long validityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenManager(
            @Value("${custom.jwt.secret-key}") String secretKey,
            @Value("${custom.jwt.expire.access}") long validityInMilliseconds,
            @Value("${custom.jwt.expire.refresh}") long refreshTokenValidityInMilliseconds) {

        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    public String createAccessToken(AuthInfo authInfo) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claim("email", authInfo.email())
                .claim("name", authInfo.name())
                .issuedAt(now)
                .expiration(validity)
                .signWith(signingKey)
                .compact();
    }

    public String createRefreshToken(AuthInfo authInfo) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .issuedAt(now)
                .expiration(validity)
                .signWith(signingKey)
                .compact();
    }
}
