package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnauthorizedException;

public class NotConversationParticipantException extends UnauthorizedException {
    public NotConversationParticipantException(String message) {
        super(message);
    }
}
