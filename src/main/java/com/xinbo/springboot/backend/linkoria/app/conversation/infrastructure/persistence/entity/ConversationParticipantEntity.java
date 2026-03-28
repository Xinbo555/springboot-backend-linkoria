package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "conversation_participant", indexes = {
        @Index(name = "idx_participant_conversation_id", columnList = "conversation_id"),
        @Index(name = "idx_participant_user_id", columnList = "user_id")
})
public class ConversationParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private Long conversationId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "user_id", nullable = false, columnDefinition = "CHAR(36)")
    private UUID userId;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private Instant joinedAt;

    protected ConversationParticipantEntity() {
    }

    public ConversationParticipantEntity(Long id, Long conversationId, UUID userId, Instant joinedAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.userId = userId;
        this.joinedAt = joinedAt;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public Long getId() {
        return id;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public UUID getUserId() {
        return userId;
    }
}
