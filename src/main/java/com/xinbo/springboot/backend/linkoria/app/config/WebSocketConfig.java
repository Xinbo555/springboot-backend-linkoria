package com.xinbo.springboot.backend.linkoria.app.config;

import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.ConversationWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuración centralizada de WebSocket para toda la aplicación

 * Registra múltiples endpoints WebSocket:
 * - /ws/conversations (mensajes en tiempo real)
 * - /ws/notifications (notificaciones) - FUTURO
 * - /ws/presence (online/offline) - FUTURO
 * - /ws/typing (indicador de escritura) - FUTURO
 * - /ws/receipt (mensajes leídos) - FUTURO

 * Cada módulo proporciona su handler correspondiente.
 * CORS permitido (ajustar en producción).
 */

@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

    private final ConversationWebSocketHandler conversationWebSocketHandler;

    public WebSocketConfig(ConversationWebSocketHandler conversationWebSocketHandler) {
        this.conversationWebSocketHandler = conversationWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        // Endpoint para mensajes
        registry
                .addHandler(conversationWebSocketHandler, "/ws/conversations")
                .setAllowedOrigins("*");
    }

    // FUTURO: Otros endpoints
    // registry.addHandler(notificationWebSocketHandler, "/ws/notifications").setAllowedOrigins("*");
    // registry.addHandler(presenceWebSocketHandler, "/ws/presence").setAllowedOrigins("*");
    // registry.addHandler(typingWebSocketHandler, "/ws/typing").setAllowedOrigins("*");
    // registry.addHandler(receiptWebSocketHandler, "/ws/receipt").setAllowedOrigins("*");
}
