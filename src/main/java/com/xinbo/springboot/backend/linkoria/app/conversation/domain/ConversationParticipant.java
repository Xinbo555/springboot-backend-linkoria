package com.xinbo.springboot.backend.linkoria.app.conversation.domain;

import java.time.Instant;
import java.util.UUID;

public class ConversationParticipant {
    private final Long id;
    private final Long conversationId;
    private final UUID userId;
    private final Instant joinedAt;

    private ConversationParticipant(Long id, Long conversationId, UUID userId, Instant joinedAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.userId = userId;
        this.joinedAt = joinedAt;
    }

    public static ConversationParticipant create(Long conversationId, UUID userId) {
        return new ConversationParticipant(null, conversationId, userId, Instant.now());
    }

    public static ConversationParticipant reconstitute(Long id, Long conversationId, UUID userId, Instant joinedAt) {
        return new ConversationParticipant(id, conversationId, userId, joinedAt);
    }

    public Long getId() {
        return id;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }
}
