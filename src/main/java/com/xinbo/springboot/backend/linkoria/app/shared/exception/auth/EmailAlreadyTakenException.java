package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
