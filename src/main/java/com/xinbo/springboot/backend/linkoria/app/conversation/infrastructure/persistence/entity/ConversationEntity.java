package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.entity;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationType;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "conversation", indexes = {
        @Index(name = "idx_conversation_channel_id", columnList = "channel_id")
})
public class ConversationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationType type;

    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected ConversationEntity() {
    }

    public ConversationEntity(Long id, ConversationType type, Long channelId, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.channelId = channelId;
        this.createdAt = createdAt;
    }

    public Long getChannelId() {
        return channelId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public ConversationType getType() {
        return type;
    }
}