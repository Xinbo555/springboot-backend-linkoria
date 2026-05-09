package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.dto.request;

public record EditMessageWebSocketRequest(
        Long messageId,
        String newContent
) {}
