package com.xinbo.springboot.backend.linkoria.app.auth.application.port.out;

import java.util.Optional;

public interface UserServicePort {

    record UserView(Long id, String username, String email, String passwordHash) {}

    //dto necesario para crear un usuario
    record CreateUserRequest(String username, String email, String passwordHash) {}

    Optional<UserView> findByEmail(String email);

    Optional<UserView> findById(Long id);

    UserView createUser(CreateUserRequest request);

    boolean existByEmail(String email);

    boolean existByUsername(String username);
}
