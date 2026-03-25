package com.xinbo.springboot.backend.linkoria.app.conversation.domain;

import java.time.Instant;
import java.util.UUID;

public class Conversation {
    private final Long id;
    private final ConversationType type;
    private final Long channelId;
    private final Instant createdAt;

    private Conversation(Long id, ConversationType type, Long channelId, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.channelId = channelId;
        created_at = createdAt;
    }
}
