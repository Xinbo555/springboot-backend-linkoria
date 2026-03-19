package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnauthorizedException;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
