package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.messaging;

import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageCreatedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageDeletedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageEditedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener de eventos de mensajes
 *
 * Se suscribe a los eventos de dominio y realiza operaciones secundarias:
 * - Logging y auditoría
 * - Actualizaciones de estadísticas
 * - Notificaciones asíncronas
 *
 * Este listener es independiente del MessageBroadcaster (que maneja WebSocket).
 * Ambos escuchan los mismos eventos pero realizan operaciones diferentes.
 */

@Component
@Slf4j
public class MessageEventSubscriber {

    /**
     * Escucha cuando se crea un nuevo mensaje
     */
    @EventListener
    public void onMessageCreated(MessageCreatedEvent event) {
        log.info("Mensaje creado - messageId: {}, conversationId: {}, userId: {}, type: {}",
                event.getMessageId(),
                event.getConversationId(),
                event.getUserId(),
                event.getMessageType());

        // Aquí irían operaciones secundarias:
        // - Guardar en auditoría
        // - Actualizar estadísticas de usuario
        // - Enviar notificaciones
        // - Incrementar contador de mensajes en conversación
    }

    /**
     * Escucha cuando se edita un mensaje
     */
    @EventListener
    public void onMessageEdited(MessageEditedEvent event) {
        log.info("Mensaje editado - messageId: {}, conversationId: {}, userId: {}, editedAt: {}",
                event.getMessageId(),
                event.getConversationId(),
                event.getUserId(),
                event.getEditedAt());

        // Aquí irían operaciones secundarias:
        // - Guardar en auditoría (con contenido anterior)
        // - Registrar timestamp de edición
        // - Notificar a otros usuarios que el mensaje fue editado
    }

    /**
     * Escucha cuando se elimina un mensaje
     */
    @EventListener
    public void onMessageDeleted(MessageDeletedEvent event) {
        log.info("Mensaje eliminado - messageId: {}, conversationId: {}, userId: {}, deletedAt: {}",
                event.getMessageId(),
                event.getConversationId(),
                event.getUserId(),
                event.getDeletedAt());

        // Aquí irían operaciones secundarias:
        // - Guardar en auditoría (soft-delete)
        // - Decrementar contador de mensajes
        // - Limpiar referencias (replies, attachments)
        // - Notificar a otros usuarios que el mensaje fue eliminado
    }
}
