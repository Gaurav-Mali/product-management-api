package com.zestindia.productmanagement.service;

import com.zestindia.productmanagement.entity.RefreshToken;
import com.zestindia.productmanagement.repository.RefreshTokenRepository;
import com.zestindia.productmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    // Set refresh token to expire in 7 days (in milliseconds)
    private final long refreshTokenDurationMs = 604800000;

    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(email).orElseThrow())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}