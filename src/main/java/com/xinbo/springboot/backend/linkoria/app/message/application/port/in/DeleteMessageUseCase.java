package com.xinbo.springboot.backend.linkoria.app.message.application.port.in;

import java.util.UUID;

public interface DeleteMessageUseCase {

    DeleteMessageResponse execute(DeleteMessageCommand command);

    record DeleteMessageCommand(Long messageId, UUID userId){}
    record DeleteMessageResponse(Long messageId, Long conversationId, boolean deleted){}
}
