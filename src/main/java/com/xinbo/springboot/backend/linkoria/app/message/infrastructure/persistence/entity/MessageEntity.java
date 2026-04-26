package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.persistence.entity;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.MessageType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "messages", indexes = {
        @Index(name = "idx_conversation_id", columnList = "conversation_id"),
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_conversation_created", columnList = "conversation_id, created_at")
})
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private Long conversationId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "user_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private UUID userId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(name = "reply_to_message_id")
    private Long replyToMessageId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected MessageEntity() {
    }

    public MessageEntity(Long id, Long conversationId, UUID userId, String content, MessageType messageType, Long replyToMessageId, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.userId = userId;
        this.content = content;
        this.messageType = messageType;
        this.replyToMessageId = replyToMessageId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getContent() {
        return content;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Long getReplyToMessageId() {
        return replyToMessageId;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UUID getUserId() {
        return userId;
    }
}
