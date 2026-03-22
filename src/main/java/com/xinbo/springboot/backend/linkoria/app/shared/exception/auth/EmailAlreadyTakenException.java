package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.ConflictException;

public class EmailAlreadyTakenException extends ConflictException {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
