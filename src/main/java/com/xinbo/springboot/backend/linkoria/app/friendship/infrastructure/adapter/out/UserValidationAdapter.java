package com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.adapter.out;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.out.UserValidationPort;
import com.xinbo.springboot.backend.linkoria.app.user.application.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserValidationAdapter implements UserValidationPort {
    private final UserService userService;

    public UserValidationAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean existsById(UUID userId) {
        return userService.existsById(userId);
    }
}
