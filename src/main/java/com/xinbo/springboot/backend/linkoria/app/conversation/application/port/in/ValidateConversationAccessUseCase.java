package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in;

import java.util.UUID;

public interface ValidateConversationAccessUseCase {
    void execute(ValidateConversationAccessCommand command);
    record ValidateConversationAccessCommand(Long conversationId, UUID userId){}
}
