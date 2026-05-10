package com.xinbo.springboot.backend.linkoria.app.conversation.rest.dto.response;


import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetConversationResponse;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationType;

import java.time.Instant;
import java.util.UUID;

public record ConversationWithMetadataResponse(
        Long id,
        ConversationType type,
        Long channelId,
        UUID targetId,
        String targetUsername,
        String targetIconUrl,
        Instant createdAt
) {
    public static ConversationWithMetadataResponse from(GetConversationResponse conversation) {
        return new ConversationWithMetadataResponse(
                conversation.id(),
                conversation.type(),
                conversation.channelId(),
                conversation.targetId(),
                conversation.targetUsername(),
                conversation.targetIconUrl(),
                conversation.createdAt()
        );
    }
}
