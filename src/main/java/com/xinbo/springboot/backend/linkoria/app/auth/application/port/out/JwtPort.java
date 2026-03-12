package com.xinbo.springboot.backend.linkoria.app.auth.application.port.out;

import java.util.UUID;

public interface JwtPort {

    String generateAccessToken(UUID userId, String username);

    UUID extractUserId(String token);

    String extractUsername(String token);

    boolean validateToken(String token);
}