package com.xinbo.springboot.backend.linkoria.app.shared.exception.server;

public class AlreadyMemberException extends RuntimeException {
    public AlreadyMemberException(String message) {
        super(message);
    }
}
