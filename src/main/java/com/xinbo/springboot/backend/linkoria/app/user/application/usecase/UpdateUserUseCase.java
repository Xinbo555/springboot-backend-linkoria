package com.xinbo.springboot.backend.linkoria.app.user.application.usecase;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.EmailAlreadyTakenException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.UsernameAlreadyTakenException;
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

    public record Input(UUID userId, String newUsername, String newEmail, String newAvatarUrl, String newBio){}

    public User execute(Input input){
        User user = userRepository.findById(input.userId())
                .orElseThrow(()->new ResourceNotFoundException("User not found: " + input.userId()));

        if(input.newUsername != null && !Username.of(input.newUsername()).equals(user.getUsername())) {
            Username newUsername = Username.of(input.newUsername());
            if(!user.getUsername().equals(newUsername) && userRepository.existsByUsername(newUsername)) {
                throw new UsernameAlreadyTakenException("Username already taken: "+ input.newUsername());
            }
            user.updateUsername(newUsername);
        }

        if(input.newEmail != null && !Email.of(input.newEmail).equals(user.getEmail())) {
            Email newEmail = Email.of(input.newEmail);
            if(!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
                throw new EmailAlreadyTakenException("Email already taken: "+ input.newUsername());
            }
            user.updateEmail(newEmail);
        }

        if (input.newAvatarUrl != null) {
            user.updateAvatarUrl(input.newAvatarUrl);
        }

        if (input.newBio != null) {
            user.updateBio(input.newBio);
        }

        return userRepository.save(user);
    }
}
