package com.xinbo.springboot.backend.linkoria.app.server.domain;

import java.time.Instant;
import java.util.UUID;

public class Server {
    private final Long id;
    private final String name;
    private final UUID ownerId;
    private final String iconUrl;
    private final String inviteCode;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Server(Long id, String name, UUID ownerId, String iconUrl, String inviteCode, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.iconUrl = iconUrl;
        this.inviteCode = inviteCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Server create(String name, UUID ownerId, String iconUrl) {
        return new Server(null, name, ownerId, iconUrl, generateInviteCode(), Instant.now(), Instant.now());
    }


    public static Server reconstitute(Long id, String name, UUID ownerId, String iconUrl, String inviteCode, Instant createdAt, Instant updatedAt) {
        return new Server(id, name, ownerId, iconUrl, inviteCode, createdAt, updatedAt);
    }

    public Server updateName(String newName) {
        return new Server(this.id, newName, this.ownerId, this.iconUrl, this.inviteCode, this.createdAt, Instant.now());
    }

    public Server updateIcon(String newIconUrl) {
        return new Server(this.id, this.name, this.ownerId, newIconUrl, this.inviteCode, this.createdAt, Instant.now());
    }

    public Server updateOwner(UUID newOwnerId) {
        return new Server(this.id, this.name, newOwnerId, this.iconUrl, this.inviteCode, this.createdAt, Instant.now());
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Long getId() {
        return id;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public String getName() {
        return name;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    //logica de negocio de dominio
    private static String generateInviteCode() {
        return UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase();
    }
}
