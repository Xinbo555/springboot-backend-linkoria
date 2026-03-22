package com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity.ServerEntity;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity.ServerMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaServerMemberRepository extends JpaRepository<ServerMemberEntity,Long> {
    List<ServerMemberEntity> findByServer_Id(Long serverId);

    Optional<ServerMemberEntity> findByServer_IdAndUserId(Long serverId, UUID userId);

    void deleteByServer_IdAndUserId(Long serverId, UUID userId);
}
