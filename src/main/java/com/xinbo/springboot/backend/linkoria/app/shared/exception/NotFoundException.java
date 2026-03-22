package com.xinbo.springboot.backend.linkoria.app.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends AppException {
    protected NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
