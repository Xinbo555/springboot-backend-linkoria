package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnauthorizedException;

public class MessageActionProhibitedException extends UnauthorizedException {
    public MessageActionProhibitedException(String message) {
        super(message);
    }
}
