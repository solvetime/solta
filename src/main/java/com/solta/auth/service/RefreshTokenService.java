package com.solta.auth.service;

import com.solta.auth.domain.RefreshToken;
import com.solta.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

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
}
