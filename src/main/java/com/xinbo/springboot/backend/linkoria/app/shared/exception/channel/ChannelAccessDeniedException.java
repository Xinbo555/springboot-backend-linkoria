package com.xinbo.springboot.backend.linkoria.app.shared.exception.channel;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnauthorizedException;

public class ChannelAccessDeniedException extends UnauthorizedException {
    public ChannelAccessDeniedException(String message) {
        super(message);
    }
}
