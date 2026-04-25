package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.ConflictException;

public class MessageAlreadyDeletedException extends ConflictException {
    public MessageAlreadyDeletedException(String message) {
        super(message);
    }
}
