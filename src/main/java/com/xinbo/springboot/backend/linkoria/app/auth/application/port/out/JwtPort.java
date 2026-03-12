package com.xinbo.springboot.backend.linkoria.app.auth.application.port.out;

//puerto que genera y valida AccessTokens
public interface JwtPort {

    String generateAccessToken(Long userId, String username);

    Long extractUserId(String token);

    String extractUsername(String token);

    boolean validateToken(String token);
}
