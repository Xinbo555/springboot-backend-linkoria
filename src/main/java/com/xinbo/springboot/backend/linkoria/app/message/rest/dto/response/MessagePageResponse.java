package com.xinbo.springboot.backend.linkoria.app.message.rest.dto.response;

import java.util.List;

/**
 * DTO para respuesta paginada de mensajes
 *
 * Endpoint: GET /api/conversations/{conversationId}/messages?cursor=X&limit=50
 *
 * Ejemplo:
 * {
 *   "messages": [
 *     { messageId: 1, content: "...", ... },
 *     { messageId: 2, content: "...", ... }
 *   ],
 *   "nextCursor": 2,
 *   "hasMore": true
 * }
 */
public record MessagePageResponse(
        List<MessageResponse> messages,
        Long nextCursor,
        boolean hasMore
) {}
