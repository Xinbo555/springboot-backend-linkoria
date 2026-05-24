package com.xinbo.springboot.backend.linkoria.app.typing.application.port.in;

import java.util.UUID;

public interface TypingUseCase {
    void startTyping(TypingCommand command);
    void stopTyping(TypingCommand command);

    record TypingCommand(
            Long conversationId,
            UUID userId
    ) {}
}
