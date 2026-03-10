package com.xinbo.springboot.backend.linkoria.app.user.domain;

import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Email;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Username;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(Email email);

    Optional<User> findByUsername(Username username);

    List<User> findByUsernameContaining(String partialUsername);

    boolean existsByEmail(Email email);

    boolean existsByUsername(Username username);

    void deleteById(UUID id);
}