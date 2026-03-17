package com.xinbo.springboot.backend.linkoria.app.shared.exception.server;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
