package com.xinbo.springboot.backend.linkoria.app.conversation.domain;

import java.time.Instant;

public class Conversation {
    private final Long id;
    private final ConversationType type;
    private final Long channelId;
    private final Instant createdAt;

    private Conversation(Long id, ConversationType type, Long channelId, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.channelId = channelId;
        this.createdAt = createdAt;
    }

    public static Conversation createDirect(){
        return new Conversation(null, ConversationType.DM,null, Instant.now());
    }

    public static Conversation createChannel(Long channelId, Long serverId) {
        if (channelId == null || serverId == null) {
            throw new IllegalArgumentException("Channel conversation requires channelId and serverId");
        }
        return new Conversation(null, ConversationType.CHANNEL,  channelId, Instant.now());
    }

    public static Conversation reconstitute(Long id, ConversationType type, Long channelId, Long serverId, Instant createdAt) {
        return new Conversation(id, type,  channelId, createdAt);
    }

    public Long getId() {
        return id;
    }

    public ConversationType getType() {
        return type;
    }

    public Long getChannelId() {
        return channelId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}
