package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;

import java.util.UUID;

public interface GetChannelConversationUseCase {
    Conversation execute(GetChannelConversationQuery query);
    record GetChannelConversationQuery(UUID requesterId, Long channelId) {}
}
