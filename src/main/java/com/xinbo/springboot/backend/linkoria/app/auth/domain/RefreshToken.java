package com.xinbo.springboot.backend.linkoria.app.auth.domain;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {

    private final Long id;
    private final String token;
    private final UUID userId;
    private final Instant expiresAt;
    private final boolean revoked;

    private RefreshToken(Long id, String token, UUID userId, Instant expiresAt, boolean revoked) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
    }

    public static RefreshToken create(UUID userId, Instant expiresAt) {
        return new RefreshToken(null, UUID.randomUUID().toString(), userId, expiresAt, false);
    }

    public static RefreshToken reconstitute(Long id, String token, UUID userId, Instant expiresAt, boolean revoked) {
        return new RefreshToken(id, token, userId, expiresAt, revoked);
    }

    public RefreshToken revoke() {
        return new RefreshToken(this.id, this.token, this.userId, this.expiresAt, true);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiresAt);
    }

    public boolean isValid() {
        return !isExpired() && !revoked;
    }

    public Long getId() { return id; }
    public String getToken() { return token; }
    public UUID getUserId() { return userId; }
    public Instant getExpiresAt() { return expiresAt; }
    public boolean isRevoked() { return revoked; }
}