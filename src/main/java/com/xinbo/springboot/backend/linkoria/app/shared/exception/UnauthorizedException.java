package com.xinbo.springboot.backend.linkoria.app.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class UnauthorizedException extends AppException {
    protected UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
