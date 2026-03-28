package com.xinbo.springboot.backend.linkoria.app.conversation.rest.dto.response;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationType;

import java.time.Instant;

public record ConversationResponse(
        Long id,
        ConversationType type,
        Long channelId,
        Instant createdAt
) {
    public static ConversationResponse from(Conversation conversation) {
        return new ConversationResponse(
                conversation.getId(),
                conversation.getType(),
                conversation.getChannelId(),
                conversation.getCreatedAt()
        );
    }
}