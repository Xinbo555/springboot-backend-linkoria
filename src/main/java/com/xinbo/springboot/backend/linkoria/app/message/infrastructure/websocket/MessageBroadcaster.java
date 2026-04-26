package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageCreatedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageDeletedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageEditedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Broadcaster de eventos de mensajes por WebSocket

 * Escucha eventos de dominio (MessageCreatedEvent, MessageEditedEvent, MessageDeletedEvent) a partir del MessageEventPublisher
 * y envía notificaciones en tiempo real a todos los usuarios conectados en esa conversación.

 * Flujo:
 * 1. MessageEventPublisherImpl publica MessageDomainEvent
 * 2. Spring ApplicationEventPublisher notifica a todos los listeners
 * 3. MessageBroadcaster recibe el evento
 * 4. Obtiene sesiones de esa conversación desde WebSocketSessionManager
 * 5. Envía mensaje JSON a cada sesión conectada

 * Formato enviado:
 * {
 *   "type": "MESSAGE_CREATED",
 *   "payload": { ...MessageResponse... },
 *   "timestamp": 1714156123456
 * }
 */

@Component
@Slf4j
public class MessageBroadcaster {

    private final WebSocketSessionManager webSocketSessionManager;
    private final ObjectMapper objectMapper;

    public MessageBroadcaster(WebSocketSessionManager webSocketSessionManager, ObjectMapper objectMapper) {
        this.webSocketSessionManager = webSocketSessionManager;
        this.objectMapper = objectMapper;
    }

    /**
     * Escucha evento de creación de mensaje y envía por WebSocket
     */
    @EventListener
    public void onMessageCreated(MessageCreatedEvent event) {
        log.debug("Broadcasting MessageCreatedEvent - messageId: {}, conversationId: {}",
                event.getMessageId(), event.getConversationId());

        Map<String, Object> wsMessage = createWebSocketMessage("MESSAGE_CREATED", event);

        broadcast(event.getConversationId(), wsMessage);
    }

    /**
     * Escucha evento de edición de mensaje y envía por WebSocket
     */
    @EventListener
    public void onMessageEdited(MessageEditedEvent event) {
        log.debug("Broadcasting MessageEditedEvent - messageId: {}, conversationId: {}",
                event.getMessageId(), event.getConversationId());

        Map<String, Object> wsMessage = createWebSocketMessage("MESSAGE_EDITED", event);

        broadcast(event.getConversationId(), wsMessage);
    }

    @EventListener
    public void onMessageDeleted(MessageDeletedEvent event) {
        log.debug("Broadcasting MessageDeletedEvent - messageId: {}, conversationId: {}",
                event.getMessageId(), event.getConversationId());

        Map<String, Object> wsMessage = createWebSocketMessage("MESSAGE_DELETED", event);

        broadcast(event.getConversationId(), wsMessage);
    }

    /**
     * Crea el estructura del mensaje WebSocket
     *
     * @param type Tipo de evento (MESSAGE_CREATED, MESSAGE_EDITED, MESSAGE_DELETED)
     * @param event Evento de dominio
     * @return Map con la estructura { type, payload }
     */
    private Map<String, Object> createWebSocketMessage(String type, Object event) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", type);
        message.put("payload", event);
        message.put("timestamp", System.currentTimeMillis());
        return message;
    }

    /**
     * Envía el mensaje a todas las sesiones de una conversación
     *
     * @param conversationId ID de la conversación
     * @param wsMessage Mensaje a enviar (ya formateado)
     */
    private void broadcast(Long conversationId, Map<String, Object> wsMessage) {
        // Obtener todas las sesiones activas en esta conversación
        Set<WebSocketSession> sessions = webSocketSessionManager.getSessionsByConversation(conversationId);

        if (sessions.isEmpty()) {
            log.debug("No hay sesiones activas para broadcast - conversationId: {}", conversationId);
            return;
        }

        log.debug("Enviando broadcast a {} sesiones - conversationId: {}", sessions.size(), conversationId);

        //Convertir mensaje a JSON
        String jsonMessage;
        try {
            jsonMessage = objectMapper.writeValueAsString(wsMessage);
        } catch (JsonProcessingException e) {
            log.error("Error serializando mensaje WebSocket", e);
            return;
        }

        TextMessage textMessage = new TextMessage(jsonMessage);

        // Enviar a cada sesión conectada (incluyendo al remitente para sincronización)
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(textMessage);
                    log.trace("Mensaje enviado - sessionId: {}", session.getId());
                } catch (IOException e) {
                    log.error("Error enviando mensaje WebSocket a sesión {} - conversationId: {}",
                            session.getId(), conversationId, e);

                    //Intentar cerrar sesión que falló
                    try {
                        session.close();
                    } catch (IOException closeError) {
                        log.error("Error cerrando sesión", closeError);
                    }
                }
            } else {
                log.debug("Sesión no está abierta - sessionId: {}", session.getId());
            }
        }

        log.debug("Broadcast completado - conversationId: {}", conversationId);
    }



}
