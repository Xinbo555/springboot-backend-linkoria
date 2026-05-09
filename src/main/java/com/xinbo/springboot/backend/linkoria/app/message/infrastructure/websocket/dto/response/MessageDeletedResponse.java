package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.dto.response;

/**
 * DTO para respuesta de eliminación enviada por STOMP
 */
public record MessageDeletedResponse(
        Long messageId,
        Long conversationId,
        boolean deleted
) {}
