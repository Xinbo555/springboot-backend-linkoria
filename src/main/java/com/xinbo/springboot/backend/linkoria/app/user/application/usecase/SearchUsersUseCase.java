package com.xinbo.springboot.backend.linkoria.app.user.application.usecase;

import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import com.xinbo.springboot.backend.linkoria.app.user.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchUsersUseCase {

    private final UserRepository userRepository;

    public SearchUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute(String partialUsername) {
        if (partialUsername == null || partialUsername.isBlank()) {
            return List.of();
        }

        return userRepository.findByUsernameContaining(partialUsername.trim());
    }
}
