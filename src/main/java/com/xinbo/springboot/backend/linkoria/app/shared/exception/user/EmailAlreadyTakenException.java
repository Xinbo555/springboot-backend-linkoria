package com.xinbo.springboot.backend.linkoria.app.shared.exception.user;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
