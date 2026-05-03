package com.xinbo.springboot.backend.linkoria.app.message.rest.dto.response;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO de respuesta para mensajes

 * Usado en:
 * - REST API responses
 * - WebSocket STOMP broadcasts

 * Contiene toda la información necesaria del mensaje
 * sin exponer detalles internos del dominio.
 */
@Schema(name = "MessageResponse", description = "DTO completo de respuesta de mensajes")
public record MessageResponse(
        Long messageId,
        Long conversationId,
        UUID userId,
        String content,
        String messageType,
        Long replyToMessageId,
        boolean isEdited,
        boolean isReply,
        Instant createdAt,
        Instant updatedAt
) {
    public static MessageResponse from(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getConversationId(),
                message.getUserId(),
                message.getContent(),
                message.getMessageType().name(),
                message.getReplyToMessageId(),
                message.isEdited(),
                message.isReply(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }
}
