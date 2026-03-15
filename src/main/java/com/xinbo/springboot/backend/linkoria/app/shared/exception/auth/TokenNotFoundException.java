package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
