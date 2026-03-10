package com.xinbo.springboot.backend.linkoria.app.user.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.user.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByUsernameContainingIgnoreCase(String partialUsername);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
