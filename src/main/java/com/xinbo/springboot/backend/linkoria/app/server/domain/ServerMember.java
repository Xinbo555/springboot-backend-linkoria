package com.xinbo.springboot.backend.linkoria.app.server.domain;

import java.time.Instant;
import java.util.UUID;

public class ServerMember {
    private final Long id;
    private final Long serverId;
    private final UUID userId;
    private final ServerRole role;
    private final Instant joinedAt;

    private ServerMember(Long id, Long serverId, UUID userId, ServerRole role, Instant joinedAt) {
        this.id = id;
        this.serverId = serverId;
        this.userId = userId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public static ServerMember create(Long serverId, UUID userId, ServerRole role) {
        return new ServerMember(null, serverId, userId, role, Instant.now());
    }

    public static ServerMember reconstitute(Long id, Long serverId, UUID userId, ServerRole role, Instant joinedAt) {
        return new ServerMember(id, serverId, userId, role, joinedAt);
    }

    public ServerMember updateRole(ServerRole newRole) {
        return new ServerMember(this.id, this.serverId, this.userId, newRole, this.joinedAt);
    }

    public Long getId() {
        return id;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public ServerRole getRole() {
        return role;
    }

    public Long getServerId() {
        return serverId;
    }

    public UUID getUserId() {
        return userId;
    }

    public boolean isOwner() {
        return this.role == ServerRole.OWNER;
    }

    public boolean hasAdminPrivileges() {
        return this.role == ServerRole.ADMIN || this.isOwner();
    }
}
