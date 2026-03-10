package com.xinbo.springboot.backend.linkoria.app.user.application.usecase;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import com.xinbo.springboot.backend.linkoria.app.user.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetUserProfileUseCase {
    private final UserRepository userRepository;

    public GetUserProfileUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }
}
