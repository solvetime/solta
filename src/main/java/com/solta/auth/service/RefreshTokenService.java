package com.solta.auth.service;

import com.solta.auth.domain.RefreshToken;
import com.solta.auth.repository.RefreshTokenRepository;
import com.solta.global.token.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenManager jwtTokenManager;

    public void saveToken(String token, Long memberId) {
        deleteToken(memberId);
        refreshTokenRepository.save(RefreshToken.builder()
                .token(token)
                .memberId(memberId)
                .build());
    }

    public void deleteToken(Long memberId) {
        refreshTokenRepository.deleteAllByMemberId(memberId);
    }

    @Transactional
    public void matches(String refreshToken, Long memberId) {
        RefreshToken savedToken = refreshTokenRepository.findByMemberId(memberId)
                .orElseThrow(IllegalArgumentException::new);

        if (!jwtTokenManager.isValid(savedToken.getToken())) {
            refreshTokenRepository.delete(savedToken);
            throw new IllegalArgumentException();
        }
        savedToken.validateSameToken(refreshToken);
    }
}
