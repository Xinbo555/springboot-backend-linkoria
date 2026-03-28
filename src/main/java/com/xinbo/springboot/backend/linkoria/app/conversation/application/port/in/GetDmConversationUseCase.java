package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;

import java.util.UUID;

public interface GetDmConversationUseCase {
    Conversation execute(GetDmConversationQuery query);
    record GetDmConversationQuery(UUID requesterId, UUID targetId) {}
}
