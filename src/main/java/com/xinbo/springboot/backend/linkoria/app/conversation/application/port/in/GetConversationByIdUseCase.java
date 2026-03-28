package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;

import java.util.UUID;

public interface GetConversationByIdUseCase {
    Conversation execute(GetConversationByIdQuery query);
    record GetConversationByIdQuery(Long conversationId) {}
}
