package com.xinbo.springboot.backend.linkoria.app.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class ConflictException extends AppException {
    protected ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
