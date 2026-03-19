package com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity.ServerEntity;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity.ServerMemberEntity;

public class ServerMemberMapper {

    private ServerMemberMapper() {
    }

    public static ServerMember toDomain(ServerMemberEntity entity) {
        return ServerMember.reconstitute(entity.getId(),
                entity.getServer().getId(),
                entity.getUserId(),
                entity.getRole(),
                entity.getJoinedAt());
    }

    public static ServerMemberEntity toEntity(ServerMember domain, ServerEntity serverEntity) {
        return new ServerMemberEntity(domain.getId(),
                serverEntity,
                domain.getUserId(),
                domain.getRole(),
                domain.getJoinedAt());
    }
}
