package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnprocessableException;

public class NonEditableMessageTypeException extends UnprocessableException {
    public NonEditableMessageTypeException(String message) {
        super(message);
    }
}
