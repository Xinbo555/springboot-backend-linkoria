package com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.dto.response;

import com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.dto.TypingAction;

import java.util.UUID;

public record TypingResponse(
        UUID userId,
        Long conversationId,
        TypingAction action
) {}
