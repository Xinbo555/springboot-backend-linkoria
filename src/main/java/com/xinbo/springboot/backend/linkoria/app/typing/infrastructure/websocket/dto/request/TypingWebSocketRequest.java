package com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.dto.request;

import com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.dto.TypingAction;

public record TypingWebSocketRequest(
        TypingAction action
) {}
