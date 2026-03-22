package com.xinbo.springboot.backend.linkoria.app.channel.domain;

import java.time.Instant;

public class ChannelCategory {
    private final Long id;
    private final Long serverId;
    private final String name;
    private final Instant createdAt;

    private ChannelCategory(Long id, Long serverId, String name, Instant createdAt) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static ChannelCategory create(Long serverId, String name) {
        return new ChannelCategory(null, serverId, name, Instant.now());
    }

    public static ChannelCategory reconstitute(Long id, Long serverId, String name, Instant createdAt) {
        return new ChannelCategory(id, serverId, name, createdAt);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getServerId() {
        return serverId;
    }
}
