package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.adapter.out;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.ConversationFacade;
import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.ConversationValidationPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConversationValidationAdapter implements ConversationValidationPort {

    private final ConversationFacade conversationFacade;

    public ConversationValidationAdapter(ConversationFacade conversationFacade) {
        this.conversationFacade = conversationFacade;
    }

    @Override
    public void isUserParticipant(Long conversationId, UUID userId) {
        conversationFacade.validateAccess(conversationId,userId);
    }

    @Override
    public void conversationExists(Long conversationId) {
        conversationFacade.validateExists(conversationId);
    }
}
