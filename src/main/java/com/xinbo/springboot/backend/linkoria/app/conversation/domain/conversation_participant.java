package com.xinbo.springboot.backend.linkoria.app.conversation.domain;

import java.time.Instant;
import java.util.UUID;

public class conversation_participant {
    private final Long conversationId;
    private final UUID userId;
    private final Instant joined_at;

    private conversation_participant(Long conversationId, UUID userId, Instant joinedAt) {
        this.conversationId = conversationId;
        this.userId = userId;
        joined_at = joinedAt;
    }
}
