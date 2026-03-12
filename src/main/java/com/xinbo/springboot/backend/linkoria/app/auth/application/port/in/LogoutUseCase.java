package com.xinbo.springboot.backend.linkoria.app.auth.application.port.in;

public interface LogoutUseCase {

    void logout(String refreshToken);
}
