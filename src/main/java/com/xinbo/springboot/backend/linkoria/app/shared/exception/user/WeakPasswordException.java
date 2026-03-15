package com.xinbo.springboot.backend.linkoria.app.shared.exception.user;

public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException(String message) {
        super(message);
    }
}
