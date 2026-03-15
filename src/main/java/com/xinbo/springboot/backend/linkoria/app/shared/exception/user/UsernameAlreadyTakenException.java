package com.xinbo.springboot.backend.linkoria.app.shared.exception.user;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
