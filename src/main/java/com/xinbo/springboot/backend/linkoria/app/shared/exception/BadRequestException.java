package com.xinbo.springboot.backend.linkoria.app.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends AppException {
    protected BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
