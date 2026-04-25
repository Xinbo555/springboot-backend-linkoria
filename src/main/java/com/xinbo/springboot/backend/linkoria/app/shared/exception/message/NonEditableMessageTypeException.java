package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

public class NonEditableMessageTypeException extends RuntimeException {
  public NonEditableMessageTypeException(String message) {
    super(message);
  }
}
