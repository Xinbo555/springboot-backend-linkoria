package com.xinbo.springboot.backend.linkoria.app.user.domain;

import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Email;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Username;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(Email email);

    Optional<User> findByUsername(Username username);

    List<User> findByUsernameContaining(String partialUsername);

    boolean existsByEmail(Email email);

    boolean existsByUsername(Username username);

    void deleteById(UUID id);

    List<User> findAllByIdIn(List<UUID> ids);
}