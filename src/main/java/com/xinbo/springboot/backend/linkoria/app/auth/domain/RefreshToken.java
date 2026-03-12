package com.xinbo.springboot.backend.linkoria.app.auth.domain;

import java.time.Instant;
import java.util.UUID;

//RefreshToken es inmutable, revoke() devuelven una nueva instancia en lugar de mutar el estado
public class RefreshToken {

    private final Long id;
    private final String token;
    private final Long userId;
    private final Instant expiresAt;
    private final boolean revoked;

    public RefreshToken(Long id, String token, Long userId, Instant expiresAt, boolean revoked) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
    }

    public static RefreshToken create(Long userId, Instant expiresAt) {
        return new RefreshToken(null, UUID.randomUUID().toString(), userId,expiresAt, false);
    }

    public static RefreshToken reconstitute(Long id, String token, Long userId, Instant expiresAt, boolean revoked) {
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

    //getters
    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Long getId() {
        return id;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}


