package com.xinbo.springboot.backend.linkoria.app.user.application.usecase;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.EmailAlreadyTakenException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.UsernameAlreadyTakenException;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import com.xinbo.springboot.backend.linkoria.app.user.domain.UserRepository;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Email;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Username;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // DTO inmutable que guarda username, email y passwordHash
    public record Input(String username, String email, String passwordHash) {}

    public User execute(Input input) {
        Username username = Username.of(input.username());
        Email email = Email.of(input.email());

        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyTakenException("Username already taken: " + input.username());
        }

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyTakenException("Email already registered: " + input.email());
        }

        User user = User.create(username, email, input.passwordHash());
        return userRepository.save(user);
    }
}
