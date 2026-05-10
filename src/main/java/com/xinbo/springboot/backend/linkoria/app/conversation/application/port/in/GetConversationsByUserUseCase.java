package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;

import java.util.List;
import java.util.UUID;

public interface GetConversationsByUserUseCase {
    List<GetConversationResponse> execute(GetConversationsByUserQuery query);
    record GetConversationsByUserQuery(UUID requesterId) {}
}
