package com.xinbo.springboot.backend.linkoria.app.auth.application.port.in;

import java.util.UUID;

public interface RegisterUseCase {

    record RegisterCommand(String username, String email, String password) {}
    record AuthResult(String accessToken, String refreshToken, UUID userId, String username) {}

    AuthResult register(RegisterCommand command);
}
