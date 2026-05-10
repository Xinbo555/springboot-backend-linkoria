package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationType;

import java.time.Instant;
import java.util.UUID;

public record GetConversationResponse(Long id,
                                      ConversationType type,
                                      Long channelId,
                                      UUID targetId,
                                      String targetUsername,
                                      String targetIconUrl,
                                      Instant createdAt
) {
    public static GetConversationResponse from(
            Conversation conversation,
            UUID targetId,
            String targetUsername,
            String targetIconUrl
    ) {
        return new GetConversationResponse(
                conversation.getId(),
                conversation.getType(),
                conversation.getChannelId(),
                targetId,
                targetUsername,
                targetIconUrl,
                conversation.getCreatedAt()
        );
    }
}
