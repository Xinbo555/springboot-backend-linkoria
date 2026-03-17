package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException(String message) {
        super(message);
    }
}
