package com.xinbo.springboot.backend.linkoria.app.shared.exception.server;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.ConflictException;

public class AlreadyMemberException extends ConflictException {
    public AlreadyMemberException(String message) {
        super(message);
    }
}
