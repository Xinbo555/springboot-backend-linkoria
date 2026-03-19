package com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.ConflictException;

public class FriendshipAlreadyExistsException extends ConflictException {
    public FriendshipAlreadyExistsException(String message) {
        super(message);
    }
}
