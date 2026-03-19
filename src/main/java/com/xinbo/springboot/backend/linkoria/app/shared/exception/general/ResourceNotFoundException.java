package com.xinbo.springboot.backend.linkoria.app.shared.exception.general;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.NotFoundException;

public class ResourceNotFoundException extends NotFoundException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
