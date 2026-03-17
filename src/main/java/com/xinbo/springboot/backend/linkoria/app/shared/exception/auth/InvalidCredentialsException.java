package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
