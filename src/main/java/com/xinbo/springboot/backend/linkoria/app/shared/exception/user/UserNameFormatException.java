package com.xinbo.springboot.backend.linkoria.app.shared.exception.user;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.BadRequestException;

public class UserNameFormatException extends BadRequestException {
    public UserNameFormatException(String message) {
        super(message);
    }
}
