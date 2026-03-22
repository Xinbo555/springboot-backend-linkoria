package com.xinbo.springboot.backend.linkoria.app.shared.exception.auth;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.NotFoundException;

public class TokenNotFoundException extends NotFoundException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
