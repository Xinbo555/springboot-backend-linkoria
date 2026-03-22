package com.xinbo.springboot.backend.linkoria.app.user.application.usecase;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import com.xinbo.springboot.backend.linkoria.app.user.domain.UserRepository;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Email;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Username;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserUseCase {

    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public record Input(UUID userId, String newUsername, String newEmail, String newAvatarUrl){}

    public User execute(Input input){
        User user = userRepository.findById(input.userId())
                .orElseThrow(()->new ResourceNotFoundException("User not found: " + input.userId()));

        if(input.newUsername != null) {
            Username newUsername = Username.of(input.newUsername());
            if(!user.getUsername().equals(input.newUsername()) && userRepository.existsByUsername(newUsername)) {
                throw new IllegalArgumentException("Username already taken: "+ input.newUsername());
            }
            user.updateUsername(newUsername);
        }

        if(input.newEmail != null) {
            Email newEmail = Email.of(input.newEmail);
            if(!user.getEmail().equals(input.newEmail()) && userRepository.existsByEmail(newEmail)) {
                throw new IllegalArgumentException("Email already taken: "+ input.newUsername());
            }
            user.updateEmail(newEmail);
        }

        if (input.newAvatarUrl() != null) {
            user.updateAvatarUrl(input.newAvatarUrl());
        }

        return userRepository.save(user);
    }
}
