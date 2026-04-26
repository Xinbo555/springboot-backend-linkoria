package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.BadRequestException;

public class MessageContentException extends BadRequestException {
    public MessageContentException(String message) {
        super(message);
    }
}
