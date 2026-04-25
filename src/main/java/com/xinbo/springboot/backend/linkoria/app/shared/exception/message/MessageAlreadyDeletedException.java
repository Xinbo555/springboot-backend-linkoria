package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

public class MessageAlreadyDeletedException extends RuntimeException {
  public MessageAlreadyDeletedException(String message) {
    super(message);
  }
}
