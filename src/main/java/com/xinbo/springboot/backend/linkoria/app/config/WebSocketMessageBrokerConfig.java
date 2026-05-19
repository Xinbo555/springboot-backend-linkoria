package com.xinbo.springboot.backend.linkoria.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * Configuración centralizada de STOMP WebSocket Message Broker

 * Configura un message broker simple en memoria para pub/sub automático.

 * Estructura de tópicos:
 * - /topic/conversation/{conversationId} → Mensajes en tiempo real
 * - /topic/typing/{conversationId} → Indicador de escritura (FUTURO)
 * - /topic/presence/{userId} → Online/offline (FUTURO)
 * - /topic/notification/{userId} → Notificaciones (FUTURO)
 * - /topic/receipt/{conversationId} → Mensajes leídos (FUTURO)

 * Prefijo de comandos:
 * - /app → Comandos del cliente (ej: /app/message/send)
 * - Los @MessageMapping en controllers escuchan estos prefijos
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    private final StompAuthChannelInterceptor stompAuthChannelInterceptor;

    public WebSocketMessageBrokerConfig(StompAuthChannelInterceptor stompAuthChannelInterceptor) {
        this.stompAuthChannelInterceptor = stompAuthChannelInterceptor;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompAuthChannelInterceptor);
    }

    /**
     * Configura el STOMP endpoint y opciones de transporte

     * Los clientes se conectan a:
     * ws://localhost:8080/ws (o el que esté configurado)

     * Luego se suscriben a:
     * SUBSCRIBE /topic/conversation/123
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*");

        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS()
                // CORS permitido (ajustar en producción)
                .setClientLibraryUrl("https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js");
    }

    /**
     * Configura el message broker

     * Usa un broker simple en memoria (SimpleBroker).
     * Registra los prefijos de destino para diferentes módulos.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");

        // Prefijo de comandos (donde los clientes envían mensajes)
        registry.setApplicationDestinationPrefixes("/app");
    }
}
