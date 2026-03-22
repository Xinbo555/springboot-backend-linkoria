package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnauthorizedException;

public class InvalidRefreshTokenException extends UnauthorizedException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
