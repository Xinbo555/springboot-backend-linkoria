package com.xinbo.springboot.backend.linkoria.app.shared.exception.message;

public class MessageActionProhibitedException extends RuntimeException {
  public MessageActionProhibitedException(String message) {
    super(message);
  }
}
