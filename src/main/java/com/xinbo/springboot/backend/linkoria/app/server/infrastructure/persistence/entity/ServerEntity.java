package com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "server")
public class ServerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "owner_id", nullable = false, columnDefinition = "CHAR(36)")
    private UUID ownerId;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "invite_code", unique = true)
    private String inviteCode;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ServerEntity() {
    }

    public ServerEntity(Long id, String name, String description, UUID ownerId, String iconUrl, String inviteCode, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.iconUrl = iconUrl;
        this.inviteCode = inviteCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
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

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServerMemberEntity> members = new ArrayList<>();
}
