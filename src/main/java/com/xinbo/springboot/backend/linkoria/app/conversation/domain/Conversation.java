package com.xinbo.springboot.backend.linkoria.app.conversation.domain;

import java.time.Instant;

public class Conversation {
    private final Long id;
    private final ConversationType type;
    private final String name;
    private final Long channelId;
    private final Instant createdAt;

    private Conversation(Long id, ConversationType type, String name, Long channelId, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.channelId = channelId;
        this.createdAt = createdAt;
    }

    public static Conversation create(ConversationType type){
        return new Conversation(null, type, null, null, Instant.now());
    }

    public static Conversation createWithChannelId(ConversationType type, Long channelId) {
        return new Conversation(null, type, null, channelId, Instant.now());
    }

    public static Conversation createWithName(ConversationType type, String name) {
        return new Conversation(null, type, name, null, Instant.now());
    }

    public static Conversation reconstitute(Long id, ConversationType type, String name, Long channelId, Instant createdAt) {
        return new Conversation(id, type, name, channelId, createdAt);
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

    public String getName() {
        return name;
    }
}
