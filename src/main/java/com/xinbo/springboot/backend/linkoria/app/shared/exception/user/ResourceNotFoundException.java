package com.xinbo.springboot.backend.linkoria.app.shared.exception.user;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
