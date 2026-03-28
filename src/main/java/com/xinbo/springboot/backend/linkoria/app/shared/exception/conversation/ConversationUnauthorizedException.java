package com.xinbo.springboot.backend.linkoria.app.shared.exception.conversation;

public class ConversationUnauthorizedException extends RuntimeException {
  public ConversationUnauthorizedException(String message) {
    super(message);
  }
}
