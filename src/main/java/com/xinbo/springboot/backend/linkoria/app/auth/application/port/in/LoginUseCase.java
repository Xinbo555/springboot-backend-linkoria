package com.xinbo.springboot.backend.linkoria.app.auth.application.port.in;

public interface LoginUseCase {

    record LoginCommand(String email, String password) {}
    record AuthResult(String accessToken, String refreshToken, Long userId, String username) {}

    AuthResult login(LoginCommand command);
}
