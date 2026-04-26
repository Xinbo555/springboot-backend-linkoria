package com.xinbo.springboot.backend.linkoria.app.message.application.port.in;

import java.util.UUID;

public interface ConversationValidationPort {

    boolean isUserParticipant(Long conversationId, UUID userId);

    boolean conversationExists(Long conversationId);
}
