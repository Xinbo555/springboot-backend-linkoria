package com.xinbo.springboot.backend.linkoria.app.auth.application.port.out;

import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    void revokeAllByUserId(Long userId);

    void deleteExpiredTokens();
}
