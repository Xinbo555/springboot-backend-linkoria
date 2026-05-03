package com.xinbo.springboot.backend.linkoria.app.auth.application.port.in;

import java.time.Instant;

public interface RefreshTokenUseCase {

    record TokenResult(String accessToken, String refreshToken, Instant refreshTokenExpiresAt) {}

    TokenResult refresh(String refreshToken);
}
