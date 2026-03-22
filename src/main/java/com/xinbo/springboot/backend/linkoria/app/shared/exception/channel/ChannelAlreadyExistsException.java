package com.xinbo.springboot.backend.linkoria.app.shared.exception.channel;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.ConflictException;

public class ChannelAlreadyExistsException extends ConflictException {
    public ChannelAlreadyExistsException(String message) {
        super(message);
    }
}
