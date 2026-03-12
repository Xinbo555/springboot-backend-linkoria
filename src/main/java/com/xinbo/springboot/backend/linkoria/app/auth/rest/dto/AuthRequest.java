package com.xinbo.springboot.backend.linkoria.app.auth.rest.dto;

/**
 * DTOs de entrada para los endpoints de autenticación.
 *
 * Actúan como contrato HTTP: representan exactamente el JSON que el cliente
 * debe enviar en el body de cada petición. Se usan únicamente en la capa
 * interface (AuthController) y nunca cruzan hacia application ni domain.
 */
public class AuthRequest {

    private AuthRequest() {}

    public record LoginRequest(String email, String password) {}

    public record RegisterRequest(String username, String email, String password) {}

    public record RefreshRequest(String refreshToken) {}

    public record LogoutRequest(String refreshToken) {}
}
