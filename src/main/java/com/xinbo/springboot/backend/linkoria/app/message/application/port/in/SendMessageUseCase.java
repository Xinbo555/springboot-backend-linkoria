package com.xinbo.springboot.backend.linkoria.app.message.application.port.in;

import java.time.Instant;
import java.util.UUID;

public interface SendMessageUseCase {
    SendMessageResponse execute(SendMessageCommand command);

    record SendMessageCommand(Long conversationId, UUID userId, String content, String messageType, Long replyToMessageId){}
    record SendMessageResponse(Long messageId, Long conversationId, UUID userId,
                               String content, String messageType, boolean isReply,
                               Instant createdAt){}
}
