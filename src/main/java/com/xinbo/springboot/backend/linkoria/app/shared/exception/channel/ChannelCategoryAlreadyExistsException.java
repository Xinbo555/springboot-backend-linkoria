package com.xinbo.springboot.backend.linkoria.app.shared.exception.channel;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.ConflictException;

public class ChannelCategoryAlreadyExistsException extends ConflictException {
    public ChannelCategoryAlreadyExistsException(String message) {
        super(message);
    }
}
