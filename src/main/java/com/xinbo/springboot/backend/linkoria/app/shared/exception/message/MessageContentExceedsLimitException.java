package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.BadRequestException;

public class MessageContentExceedsLimitException extends BadRequestException {
    public MessageContentExceedsLimitException(String message) {
        super(message);
    }
}
