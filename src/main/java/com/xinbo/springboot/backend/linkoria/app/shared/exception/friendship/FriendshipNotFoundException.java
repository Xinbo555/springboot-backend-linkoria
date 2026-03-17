package com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship;

public class FriendshipNotFoundException extends RuntimeException {
    public FriendshipNotFoundException(String message) {
        super(message);
    }
}
