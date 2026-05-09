package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.dto.request;

public record SendMessageWebSocketRequest(
        String content,
        String messageType,
        Long replyToMessageId
) {}
