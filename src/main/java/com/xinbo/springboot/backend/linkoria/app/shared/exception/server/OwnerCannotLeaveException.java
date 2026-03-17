package com.xinbo.springboot.backend.linkoria.app.shared.exception.server;

public class OwnerCannotLeaveException extends RuntimeException {
    public OwnerCannotLeaveException(String message) {
        super(message);
    }
}
