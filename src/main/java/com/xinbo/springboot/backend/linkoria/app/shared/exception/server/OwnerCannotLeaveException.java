package com.xinbo.springboot.backend.linkoria.app.shared.exception.server;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnprocessableException;

public class OwnerCannotLeaveException extends UnprocessableException {
    public OwnerCannotLeaveException(String message) {
        super(message);
    }
}
