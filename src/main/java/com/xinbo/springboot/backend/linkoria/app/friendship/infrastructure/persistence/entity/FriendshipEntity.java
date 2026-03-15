package com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.persistence.entity;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "friendships", indexes = {
        @Index(name = "idx_friendship_sender_receiver", columnList = "sender_id, receiver_id"),
        @Index(name = "idx_friendship_sender_status", columnList = "sender_id, status"),
        @Index(name = "idx_friendship_receiver_status", columnList = "receiver_id, status")
})
public class FriendshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "sender_id", nullable = false, columnDefinition = "CHAR(36)")
    private UUID senderId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "receiver_id", nullable = false, columnDefinition = "CHAR(36)")
    private UUID receiverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FriendshipStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "blocked_by", columnDefinition = "CHAR(36)")
    private UUID blockedBy;

    protected FriendshipEntity() {}

    public FriendshipEntity(Long id, UUID senderId, UUID receiverId, FriendshipStatus status, Instant createdAt, Instant updatedAt, UUID blockedBy) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.blockedBy = blockedBy;
    }

    public UUID getBlockedBy() {
        return blockedBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
