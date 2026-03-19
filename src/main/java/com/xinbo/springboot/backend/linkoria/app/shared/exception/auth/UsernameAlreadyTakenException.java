package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.ConflictException;

public class UsernameAlreadyTakenException extends ConflictException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
