package com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnprocessableException;

public class FriendshipStatusException extends UnprocessableException {
    public FriendshipStatusException(String message) {
        super(message);
    }
}
