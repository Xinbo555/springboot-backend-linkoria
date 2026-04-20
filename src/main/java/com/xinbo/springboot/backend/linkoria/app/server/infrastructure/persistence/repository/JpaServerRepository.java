package com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaServerRepository extends JpaRepository<ServerEntity,Long> {
    Optional<ServerEntity> findByInviteCode(String inviteCode);

    @Query("""
    SELECT DISTINCT s
    FROM ServerEntity s
    LEFT JOIN s.members sm
    WHERE s.ownerId = :userId OR sm.userId = :userId
""")
    List<ServerEntity> findAllByUserParticipation(UUID userId);
}
