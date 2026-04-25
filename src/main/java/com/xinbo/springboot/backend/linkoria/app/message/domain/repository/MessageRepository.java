package com.xinbo.springboot.backend.linkoria.app.message.domain.repository;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    Message save(Message message);

    Optional<Message> findById(Long messageId);

    List<Message> findByConversationId(Long conversationId);

    /**
     * Obtiene mensajes de una conversación con pagination por cursor.
     *
     * @param conversationId ID de la conversación
     * @param cursor ID del último mensaje visto (null si es la primera página)
     * @param limit Cantidad de mensajes a retornar
     * @param direction BACKWARDS = hacia atrás en el tiempo, FORWARDS = hacia adelante
     * @return Lista de mensajes ordenados
     */
    List<Message> findByConversationIdCursorPaginated(Long conversationId,
                                                      Long cursor,
                                                      int limit,
                                                      PaginationDirection direction);

    List<Message> findByReplyToMessageId(Long replyToMessageId);

    List<Message> findByConversationIdAndUserId(Long conversationId, java.util.UUID userId);

    boolean existsById(Long messageId);

    long countByConversationId(Long conversationId);

    void deleteById(Long messageId);

    enum PaginationDirection {
        BACKWARDS,  // Hacia el pasado (mensajes más antiguos)
        FORWARDS    // Hacia el futuro (mensajes más nuevos)
    }
}
