package com.xinbo.springboot.backend.linkoria.app.shared.user;

import java.util.UUID;

public interface UserValidationPort {
    boolean existsById(UUID userId);
}
