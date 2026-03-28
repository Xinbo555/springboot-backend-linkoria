package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out;

import java.util.UUID;

public interface UserValidationPort {
    void validateExists(UUID userId);
}
