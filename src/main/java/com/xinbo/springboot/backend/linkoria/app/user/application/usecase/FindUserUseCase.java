package com.xinbo.springboot.backend.linkoria.app.user.application.usecase;

import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import com.xinbo.springboot.backend.linkoria.app.user.domain.UserRepository;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Email;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Username;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FindUserUseCase {

    private final UserRepository userRepository;

    public FindUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(Email.of(email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(Email.of(email));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(Username.of(username));
    }

    public boolean existsById(UUID id) {
        return userRepository.findById(id).isPresent();
    }

    public List<User> findByIds(List<UUID> ids) {
        return userRepository.findAllByIdIn(ids);
    }
}
