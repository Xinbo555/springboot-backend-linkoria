package com.xinbo.springboot.backend.linkoria.app.shared.exception.channel;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.NotFoundException;

public class ChannelNotFoundException extends NotFoundException {
    public ChannelNotFoundException(String message) {
        super(message);
    }
}
