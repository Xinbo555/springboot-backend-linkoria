package com.xinbo.springboot.backend.linkoria.app.shared.exception.conversation;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnauthorizedException;

public class ConversationUnauthorizedException extends UnauthorizedException {
    public ConversationUnauthorizedException(String message) {
        super(message);
    }
}
