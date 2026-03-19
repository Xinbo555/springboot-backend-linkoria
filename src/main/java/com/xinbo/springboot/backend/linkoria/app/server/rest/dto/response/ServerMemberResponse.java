package com.xinbo.springboot.backend.linkoria.app.server.rest.dto.response;

import com.xinbo.springboot.backend.linkoria.app.server.application.ServerMemberDetail;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRole;

import java.time.Instant;
import java.util.UUID;

public record ServerMemberResponse(UUID userId, String username, String avatarUrl, ServerRole role, Instant joinedAt) {
    public static ServerMemberResponse from(ServerMemberDetail detail) {
        return new ServerMemberResponse(
                detail.member().getUserId(),
                detail.username(),
                detail.avatarUrl(),
                detail.member().getRole(),
                detail.member().getJoinedAt()
        );
    }
}
