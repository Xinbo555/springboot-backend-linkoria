package com.xinbo.springboot.backend.linkoria.app.shared.exception.channel;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.NotFoundException;

public class ChannelCategoryNotFoundException extends NotFoundException {
    public ChannelCategoryNotFoundException(String message) {
        super(message);
    }
}
