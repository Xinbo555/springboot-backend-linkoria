package com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens", indexes = {
        @Index(name = "idx_refresh_token_token", columnList = "token"),
        @Index(name = "idx_refresh_token_user_id", columnList = "user_id")
})
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true, length = 36)
    private String token;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createAt;

    protected RefreshTokenEntity() {}

    public RefreshTokenEntity(Long id, String token, Long userId, Instant expiresAt, boolean revoked, Instant createAt) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.createAt = createAt;
    }

    //callback antes de insert
    @PrePersist
    public void prePersist() {
        if (this.createAt == null) {
            this.createAt = Instant.now();
        }
    }

    public Instant getCreateAt() {
        return createAt;
    }

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
