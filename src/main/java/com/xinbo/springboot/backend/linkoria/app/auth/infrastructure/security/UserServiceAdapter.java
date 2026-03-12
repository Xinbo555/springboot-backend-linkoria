package com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.security;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.UserServicePort;
import com.xinbo.springboot.backend.linkoria.app.user.application.usecase.CreateUserUseCase;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
                .map(u -> new UserView(u.id(), u.username(), u.email(), u.passwordHash()));
    }

    @Override
    public Optional<UserView> findById(Long id) {
        return findUserUseCase.findById(id)
                .map(u -> new UserView(u.id(), u.username(), u.email(), u.passwordHash()));
    }

    @Override
    public UserView createUser(CreateUserRequest request) {
        var created = createUserUseCase.create(
                new CreateUserUseCase.CreateUserCommand(
                        request.username(),
                        request.email(),
                        request.passwordHash()
                )
        );
        return new UserView(created.id(), created.username(), created.email(), created.passwordHash());
    }

    @Override
    public boolean existsByEmail(String email) {
        return findUserUseCase.exi(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return findUserUseCase.existsByUsername(username);
    }
}
