package com.xinbo.springboot.backend.linkoria.app.channel.domain;

import java.time.Instant;

public class Channel {
    private final Long id;
    private final Long serverId;
    private final Long categoryId;
    private final String name;
    private final Instant createdAt;

    private Channel(Long id, Long serverId, Long categoryId, String name, Instant createdAt) {
        this.id = id;
        this.serverId = serverId;
        this.categoryId = categoryId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static Channel create(Long serverId, String name) {
        return new Channel(null, serverId, null, name, Instant.now());
    }

    public static Channel createWithCategory(Long serverId, Long categoryId, String name) {
        return new Channel(null, serverId, categoryId, name, Instant.now());
    }

    public static Channel reconstitute(Long id, Long serverId, Long categoryId, String name, Instant createdAt) {
        return new Channel(id, serverId, categoryId, name, createdAt);
    }

    public Long getCategoryId() {
        return categoryId;
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
