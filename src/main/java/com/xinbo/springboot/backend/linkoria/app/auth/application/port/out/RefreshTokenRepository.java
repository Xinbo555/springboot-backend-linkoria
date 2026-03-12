package com.xinbo.springboot.backend.linkoria.app.auth.application.port.out;

import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    void revokeAllByUserId(UUID userId);

    void deleteExpiredTokens();
}