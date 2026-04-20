package com.xinbo.springboot.backend.linkoria.app.shared.exception.user;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.BadRequestException;

public class EmailFormatException extends BadRequestException {
    public EmailFormatException(String message) {
        super(message);
    }
}
