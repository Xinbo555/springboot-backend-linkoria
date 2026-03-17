package com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship;

public class FriendshipAlreadyExistsException extends RuntimeException {
    public FriendshipAlreadyExistsException(String message) {
        super(message);
    }
}
