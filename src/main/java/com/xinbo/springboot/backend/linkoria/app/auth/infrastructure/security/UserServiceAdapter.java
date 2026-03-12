package com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.security;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.UserServicePort;
import com.xinbo.springboot.backend.linkoria.app.user.application.service.UserService;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

//UserServiceAdapter traduce y expone los casos de uso del módulo de usuarios para que el módulo de autenticación pueda
//interactuar con ellos (convirtiendo el pojo de User a un UserView de este dominio) sin acoplarse al dominio interno de usuarios
@Component
public class UserServiceAdapter implements UserServicePort {

    private final UserService userService;

    public UserServiceAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<UserView> findByEmail(String email) {
        return userService.findByEmail(email)
                .map(u -> new UserView(u.getId(), u.getUsername().getValue(), u.getEmail().getValue(), u.getPasswordHash()));
    }

    @Override
    public Optional<UserView> findById(UUID id) {
        return userService.findById(id)
                .map(u -> new UserView(u.getId(), u.getUsername().getValue(), u.getEmail().getValue(), u.getPasswordHash()));
    }

    @Override
    public UserView createUser(CreateUserRequest request) {
        User user = userService.createUser(request.username(), request.email(), request.passwordHash());
        return new UserView(user.getId(), user.getUsername().getValue(), user.getEmail().getValue(), user.getPasswordHash());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userService.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userService.existsByUsername(username);
    }
}