package com.xinbo.springboot.backend.linkoria.app.message.application.port.in;

import java.util.UUID;

public interface ConversationValidationPort {

    void isUserParticipant(Long conversationId, UUID userId);

    void conversationExists(Long conversationId);
}
