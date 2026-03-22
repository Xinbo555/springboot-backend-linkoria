package com.xinbo.springboot.backend.linkoria.app.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class UnprocessableException extends AppException {
    protected UnprocessableException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
