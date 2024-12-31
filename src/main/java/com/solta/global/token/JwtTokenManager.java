package com.solta.global.token;

import com.solta.auth.dto.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
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
                .claim("id", authInfo.id())
                .claim("email", authInfo.email())
                .claim("name", authInfo.name())
                .issuedAt(now)
                .expiration(validity)
                .signWith(signingKey)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .issuedAt(now)
                .expiration(validity)
                .signWith(signingKey)
                .compact();
    }

    public String getPayload(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public AuthInfo getParsedClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith((SecretKey) signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            Long id = e.getClaims().get("id", Long.class);
            String email = e.getClaims().get("email", String.class);
            String name = e.getClaims().get("name", String.class);
            return new AuthInfo(id, email, name);
        }

        Long id = claims.get("id", Long.class);
        String email = claims.get("email", String.class);
        String name = claims.get("name", String.class);
        return new AuthInfo(id, email, name);
    }

    public boolean isValid(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);

            return !claimsJws.getPayload().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
