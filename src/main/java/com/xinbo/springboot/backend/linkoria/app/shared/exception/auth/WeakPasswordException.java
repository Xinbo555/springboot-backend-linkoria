package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.BadRequestException;

public class WeakPasswordException extends BadRequestException {
    public WeakPasswordException(String message) {
        super(message);
    }
}
