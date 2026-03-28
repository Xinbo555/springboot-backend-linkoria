package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in;

import java.util.List;
import java.util.UUID;

public interface FindParticipantsByConversationIdUseCase {
    List<UUID> execute(FindParticipantsQuery query);
    record FindParticipantsQuery(Long conversationId) {}
}
