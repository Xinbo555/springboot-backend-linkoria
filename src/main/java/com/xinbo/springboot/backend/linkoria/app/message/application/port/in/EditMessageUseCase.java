package com.xinbo.springboot.backend.linkoria.app.message.application.port.in;

import java.time.Instant;
import java.util.UUID;

public interface EditMessageUseCase {
    EditMessageResponse execute(EditMessageCommand command);

    record EditMessageCommand(Long messageId, UUID userId, String newContent){}
    record EditMessageResponse(Long messageId, Long conversationId, UUID userId,
                               String content, boolean isEdited, Instant updatedAt){}
}
