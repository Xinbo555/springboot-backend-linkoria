package com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.NotFoundException;

public class ReceiverIdNotFoundException extends NotFoundException {
    public ReceiverIdNotFoundException(String message) {
        super(message);
    }
}
