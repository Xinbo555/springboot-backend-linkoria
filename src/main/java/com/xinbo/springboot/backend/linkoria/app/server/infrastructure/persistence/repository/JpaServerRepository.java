package com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaServerRepository extends JpaRepository<ServerEntity,Long> {
    Optional<ServerEntity> findByInviteCode(String inviteCode);
}
