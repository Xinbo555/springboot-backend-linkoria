package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket;

import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageCreatedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageDeletedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageEditedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.dto.response.MessageDeletedResponse;
import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.dto.response.MessageEditedResponse;
import com.xinbo.springboot.backend.linkoria.app.message.rest.dto.response.MessageResponse;
import com.xinbo.springboot.backend.linkoria.app.shared.util.websocket.WebSocketNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Broadcaster de eventos de mensajes a través de STOMP

 * Escucha eventos de dominio (MessageCreatedEvent, MessageEditedEvent, MessageDeletedEvent)
 * y los envía automáticamente a los tópicos STOMP correspondientes.

 * STOMP se encarga de:
 * - Enviar a todos los clientes suscritos en ese tópico
 * - Gestionar sesiones automáticamente
 * - Sincronizar incluyendo al remitente

 * Flujo:
 * 1. MessageEventPublisherImpl publica MessageDomainEvent
 * 2. MessageBroadcaster escucha @EventListener
 * 3. Envía a /topic/conversation/{conversationId}
 * 4. STOMP distribuye a todos los suscritos
 */

@Component
@Slf4j
public class MessageBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Escucha evento de creación de mensaje y envía por WebSocket de forma asíncrona.
     */
    @Async
    @EventListener
    public void onMessageCreated(MessageCreatedEvent event) {
        log.debug("Broadcasting MessageCreatedEvent a STOMP - messageId: {}, conversationId: {}",
                event.getMessageId(), event.getConversationId());

        MessageResponse payload = new MessageResponse(
                event.getMessageId(),
                event.getConversationId(),
                event.getUserId(),
                event.getContent(),
                event.getMessageType().name(),
                event.getReplyToMessageId(),
                false,
                event.getReplyToMessageId() != null,
                event.getCreatedAt(),
                event.getCreatedAt()
        );

        // Enviamos con el Wrapper para que el frontend identifique el tipo de acción
        broadcast(event.getConversationId(), "MESSAGE_CREATED", payload);
    }

    /**
     * Escucha evento de edición de mensaje y envía por WebSocket de forma asíncrona.
     */
    @Async
    @EventListener
    public void onMessageEdited(MessageEditedEvent event) {
        log.debug("Broadcasting MessageEditedEvent - messageId: {}, conversationId: {}",
                event.getMessageId(), event.getConversationId());

        MessageEditedResponse payload = new MessageEditedResponse(
                event.getMessageId(),
                event.getConversationId(),
                event.getUserId(),
                event.getNewContent(),
                true,
                event.getEditedAt()
        );

        broadcast(event.getConversationId(), "MESSAGE_EDITED", payload);
    }

    /**
     * Escucha evento de eliminación de mensaje y envía por STOMP de forma asíncrona.
     */
    @Async
    @EventListener
    public void onMessageDeleted(MessageDeletedEvent event) {
        log.debug("Broadcasting MessageDeletedEvent - messageId: {}, conversationId: {}",
                event.getMessageId(), event.getConversationId());

        MessageDeletedResponse payload = new MessageDeletedResponse(
                event.getMessageId(),
                event.getConversationId(),
                true
        );

        broadcast(event.getConversationId(), "MESSAGE_DELETED", payload);
    }

    /**
     * Miembro privado auxiliar para envolver el mensaje y enviarlo al tópico.
     */
    private void broadcast(Long conversationId, String type, Object payload) {
        WebSocketNotification<Object> notification = new WebSocketNotification<>(
                type,
                payload,
                Instant.now().toEpochMilli()
        );

        String destination = "/topic/conversation/" + conversationId;
        messagingTemplate.convertAndSend(destination, notification);
        log.debug("Evento {} enviado a {}", type, destination);
    }
}