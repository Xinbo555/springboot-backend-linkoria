package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * Handler de WebSocket para conversaciones

 * Gestiona:
 * - Conexiones nuevas: Registrar sesión en la conversación
 * - Desconexiones: Desregistrar sesión
 * - Errores: Logging y cleanup

 * Esperado:
 * El cliente envía un mensaje inicial con { "conversationId": 123 }
 * para que el handler sepa a qué conversación se está suscribiendo.

 * Estructura del mensaje esperado:
 * {
 *   "type": "SUBSCRIBE",
 *   "conversationId": 123
 * }
 */
@Component
@Slf4j
public class ConversationWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager webSocketSessionManager;
    private final ObjectMapper objectMapper;

    private static final String CONVERSATION_ID_ATTR = "conversationId";

    public ConversationWebSocketHandler(WebSocketSessionManager webSocketSessionManager, ObjectMapper objectMapper) {
        this.webSocketSessionManager = webSocketSessionManager;
        this.objectMapper = objectMapper;
    }

    /**
     * Llamado cuando se establece una nueva conexión WebSocket

     * El cliente debe enviar el primer mensaje indicando a qué conversación se suscribe.
     * Esperamos que el mensaje contenga: { "conversationId": 123 }
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Nueva conexión WebSocket establecida - sessionId: {}", session.getId());
        // En este punto no se registra aún, esperamos el mensaje de SUBSCRIBE
    }

    /**
     * Llamado cuando se recibe un mensaje del cliente
     *
     * El cliente envía: { "type": "SUBSCRIBE", "conversationId": 123 }
     * Registramos la sesión para esa conversación.
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (!(message instanceof TextMessage textMessage)) {
            log.warn("Mensaje no-text recibido, ignorando");
            return;
        }

        try {
            String payload = textMessage.getPayload();
            JsonNode jsonNode = objectMapper.readTree(payload);

            String type = jsonNode.get("type").asText();

            if ("SUBSCRIBE".equals(type)) {
                Long conversationId = jsonNode.get("conversationId").asLong();

                // Guardar conversationId en la sesión para poder accederla después
                session.getAttributes().put(CONVERSATION_ID_ATTR, conversationId);

                // Registrar sesión en el gestor
                webSocketSessionManager.registerSession(conversationId, session);

                // Enviar confirmación al cliente
                sendConfirmation(conversationId, session);
            } else {
                log.warn("Tipo de mensaje desconocido: {}", type);
            }
        } catch (Exception e) {
            log.error("Error procesando mensaje WebSocket", e);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Conexión WebSocket cerrada - sessionId: {}, status: {}",
                session.getId(), status.getReason());

        webSocketSessionManager.unregisterSession(session);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Error en transporte WebSocket - sessionId: {}", session.getId(), exception);

        // Intentar cerrar la sesión
        if (session.isOpen()) {
            session.close(CloseStatus.SERVER_ERROR);
        }

        // Deregistrar sesión
        webSocketSessionManager.unregisterSession(session);
    }

    /**
     * Llamado cuando se cierra la conexión
     * Desregistramos la sesión de todas las conversaciones
     */


    private void sendConfirmation(Long conversationId, WebSocketSession session) throws IOException {
        String confirmation = objectMapper.writeValueAsString(
                new HashMap<String, Object>(){{
                    put("type", "SUBSCRIBED");
                    put("conversationId", conversationId);
                    put("message", "Suscrito correctamente a la conversación");
                }}
        );

        session.sendMessage(new TextMessage(confirmation));
    }


}
