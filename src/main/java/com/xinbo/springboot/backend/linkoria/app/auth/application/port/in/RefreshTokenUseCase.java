package com.xinbo.springboot.backend.linkoria.app.auth.application.port.in;

public interface RefreshTokenUseCase {

    record TokenResult(String accessToken, String refreshToken) {}

    TokenResult refresh(String refreshToken);
}
