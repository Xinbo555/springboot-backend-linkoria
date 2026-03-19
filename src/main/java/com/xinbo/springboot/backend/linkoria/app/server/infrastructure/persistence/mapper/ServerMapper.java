package com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity.ServerEntity;

public class ServerMapper {
    private ServerMapper() {
    }

    public static Server toDomain(ServerEntity entity) {
        return Server.reconstitute(entity.getId(),
                entity.getName(),
                entity.getOwnerId(),
                entity.getIconUrl(),
                entity.getInviteCode(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static ServerEntity toEntity(Server domain) {
        return new ServerEntity(domain.getId(),
                domain.getName(),
                domain.getOwnerId(),
                domain.getIconUrl(),
                domain.getInviteCode(),
                domain.getCreatedAt(),
                domain.getUpdatedAt());
    }
}
