package com.xinbo.springboot.backend.linkoria.app.auth.application.port.out;

import java.util.Optional;
import java.util.UUID;

public interface UserServicePort {

    record UserView(UUID id, String username, String email, String passwordHash) {}
    //dto necesario para crear un usuario
    record CreateUserRequest(String username, String email, String passwordHash) {}

    Optional<UserView> findByEmail(String email);

    Optional<UserView> findById(UUID id);

    UserView createUser(CreateUserRequest request);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
