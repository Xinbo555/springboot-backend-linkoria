package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.BadRequestException;

public class MalformedMediaUrlException extends BadRequestException {
    public MalformedMediaUrlException(String message) {
        super(message);
    }
}
