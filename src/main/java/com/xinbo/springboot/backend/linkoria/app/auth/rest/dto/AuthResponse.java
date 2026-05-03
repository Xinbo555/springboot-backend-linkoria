package com.xinbo.springboot.backend.linkoria.app.auth.rest.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTOs de salida para los endpoints de autenticación.
 *
 * Representan el JSON que el servidor devuelve al cliente.
 * Se usan únicamente en la capa interface (AuthController) y nunca
 * exponen objetos del dominio directamente hacia fuera.
 */
public class AuthResponse {
    public record AuthTokenResponse(
            String accessToken,
            String refreshToken,
            String tokenType,
            UUID userId,
            String username,
            Instant refreshTokenExpiresAt
    ) {
        public static AuthTokenResponse of(String accessToken, String refreshToken, UUID userId, String username, Instant refreshTokenExpiresAt) {
            return new AuthTokenResponse(accessToken, refreshToken, "Bearer", userId, username, refreshTokenExpiresAt);
        }
    }

    public record TokenRefreshResponse(
            String accessToken,
            String refreshToken,
            String tokenType,
            Instant refreshTokenExpiresAt
    ) {
        public static TokenRefreshResponse of(String accessToken, String refreshToken, Instant refreshTokenExpiresAt) {
            return new TokenRefreshResponse(accessToken, refreshToken, "Bearer", refreshTokenExpiresAt);
        }
    }

    public record MessageResponse(String message) {}
}
