package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;

import java.util.UUID;

public interface CreateChannelConversationUseCase {
    Conversation create(CreateChConversationCommand command);

    record CreateChConversationCommand(UUID requesterId, Long channelId) {}
}
