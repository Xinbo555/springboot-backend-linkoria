package com.xinbo.springboot.backend.linkoria.app.message.rest.dto.request;

public record SendMessageRequest(
        String content,
        String messageType,
        Long replyToMessageId
) {}
