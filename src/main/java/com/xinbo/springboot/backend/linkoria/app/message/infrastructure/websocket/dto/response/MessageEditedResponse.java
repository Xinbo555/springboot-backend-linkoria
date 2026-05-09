package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.dto.response;

import java.time.Instant;

/**
 * DTO para respuesta de edición enviada por STOMP
 */
public record MessageEditedResponse(
        Long messageId,
        Long conversationId,
        java.util.UUID userId,
        String content,
        boolean isEdited,
        Instant updatedAt
) {}
