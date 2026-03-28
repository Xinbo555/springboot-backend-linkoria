package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;

import java.util.UUID;

public interface CreateDmConversationUseCase {
    Conversation execute(CreateDmConversationCommand command);
    record CreateDmConversationCommand(UUID requesterId, UUID targetId) {}
}
