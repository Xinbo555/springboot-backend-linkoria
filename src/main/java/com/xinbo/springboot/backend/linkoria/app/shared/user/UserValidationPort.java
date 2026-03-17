package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.out;

import java.util.UUID;

public interface UserValidationPort {
    boolean existsById(UUID userId);
}
