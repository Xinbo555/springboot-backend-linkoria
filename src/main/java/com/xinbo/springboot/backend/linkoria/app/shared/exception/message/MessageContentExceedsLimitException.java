package com.xinbo.springboot.backend.linkoria.app.shared.exception;

public class MessageContentExceedsLimitException extends RuntimeException {
  public MessageContentExceedsLimitException(String message) {
    super(message);
  }
}
