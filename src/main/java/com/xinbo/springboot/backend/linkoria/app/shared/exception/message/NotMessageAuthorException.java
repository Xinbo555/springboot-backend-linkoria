package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnauthorizedException;

public class NotMessageAuthorException extends UnauthorizedException {
    public NotMessageAuthorException(String message) {
        super(message);
    }
}
