package com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity;

import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRole;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "server_member", uniqueConstraints = @UniqueConstraint(columnNames = {"server_Id", "user_id"}))
public class ServerMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_server_member_server"))
    private ServerEntity server;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "user_id", nullable = false, columnDefinition = "CHAR(36)")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ServerRole role;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

    protected ServerMemberEntity() {
    }

    public ServerMemberEntity(Long id, ServerEntity server, UUID userId, ServerRole role, Instant joinedAt) {
        this.id = id;
        this.server = server;
        this.userId = userId;
        this.role = role;
        this.joinedAt = joinedAt;
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

    public UUID getUserId() {
        return userId;
    }

    public ServerEntity getServer() {
        return server;
    }
}
