package com.xinbo.springboot.backend.linkoria.app.user.application;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.user.application.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserFacade {

    private final UserService userService;

    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    public void validateExists(UUID userId) {
        if(!userService.existsById(userId)) {
            throw new ResourceNotFoundException("User not found: " + userId);
        }
    }
}
