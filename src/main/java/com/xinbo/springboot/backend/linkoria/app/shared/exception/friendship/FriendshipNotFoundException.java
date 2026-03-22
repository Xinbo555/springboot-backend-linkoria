package com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.NotFoundException;

public class FriendshipNotFoundException extends NotFoundException {
    public FriendshipNotFoundException(String message) {
        super(message);
    }
}
