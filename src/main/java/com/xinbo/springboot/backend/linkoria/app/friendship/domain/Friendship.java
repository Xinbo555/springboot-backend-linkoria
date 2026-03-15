package com.xinbo.springboot.backend.linkoria.app.friendship.domain;

import java.time.Instant;
import java.util.UUID;

public class Friendship {
    private final Long id;
    private final UUID senderId;
    private final UUID receiverId;
    private final FriendshipStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final UUID blockedBy;

    private Friendship(Long id, UUID senderId, UUID receiverId, FriendshipStatus status, Instant createdAt, Instant updatedAt, UUID blockedBy) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.blockedBy = blockedBy;
    }

    public static Friendship create(UUID senderId, UUID receiverId){
        Instant createdAt = Instant.now();
        return new Friendship(null,senderId,receiverId,FriendshipStatus.PENDING,createdAt,createdAt,null);
    }

    public static Friendship reconstitute(Long id, UUID senderId, UUID receiverId, FriendshipStatus status, Instant createdAt, Instant updatedAt, UUID blockedBy) {
        return new Friendship(id, senderId, receiverId, status, createdAt, updatedAt, blockedBy);
    }

    public Friendship accept() {
        return new Friendship(this.id,this.senderId,this.receiverId,FriendshipStatus.ACCEPTED,this.createdAt,Instant.now(), this.blockedBy);
    }

    public Friendship decline() {
        return new Friendship(this.id,this.senderId, this.receiverId,FriendshipStatus.DECLINED, this.createdAt, Instant.now(), this.blockedBy);
    }

    public Friendship block(UUID blockerId) {
        return new Friendship(this.id,this.senderId, this.receiverId,FriendshipStatus.BLOCKED, this.createdAt, Instant.now(), blockerId);
    }

    public Friendship remove() {
        return new Friendship(this.id,this.senderId, this.receiverId,FriendshipStatus.REMOVED, this.createdAt, Instant.now(), this.blockedBy);
    }

    public Friendship unblock() {
        return remove();
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

    public UUID getBlockedBy() {
        return blockedBy;
    }
}
