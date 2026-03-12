package com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.security;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.UserServicePort;
import com.xinbo.springboot.backend.linkoria.app.user.application.usecase.CreateUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.user.application.usecase.FindUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

//UserServiceAdapter traduce y expone los casos de uso del módulo de usuarios para que el módulo de autenticación pueda
//interactuar con ellos (convirtiendo el pojo de User a un UserView de este dominio) sin acoplarse al dominio interno de usuarios
@Component
public class UserServiceAdapter implements UserServicePort {

    private final FindUserUseCase findUserUseCase;
    private final CreateUserUseCase createUserUseCase;

    public UserServiceAdapter(FindUserUseCase findUserUseCase, CreateUserUseCase createUserUseCase) {
        this.findUserUseCase = findUserUseCase;
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    public Optional<UserView> findByEmail(String email) {
        return findUserUseCase.findByEmail(email)
                .map(u -> new UserView(u.getId(), u.getUsername().getValue(), u.getEmail().getValue(), u.getPasswordHash()));
    }

    @Override
    public Optional<UserView> findById(UUID id) {
        return findUserUseCase.findById(id)
                .map(u -> new UserView(u.getId(), u.getUsername().getValue(), u.getEmail().getValue(), u.getPasswordHash()));
    }

    @Override
    public UserView createUser(CreateUserRequest request) {
        User user = createUserUseCase.execute(
                new CreateUserUseCase.Input(request.username(), request.email(), request.passwordHash())
        );
        return new UserView(user.getId(), user.getUsername().getValue(), user.getEmail().getValue(), user.getPasswordHash());
    }

    @Override
    public boolean existsByEmail(String email) {
        return findUserUseCase.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return findUserUseCase.existsByUsername(username);
    }
}