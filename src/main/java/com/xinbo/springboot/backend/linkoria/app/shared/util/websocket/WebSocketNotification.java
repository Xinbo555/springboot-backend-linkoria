package com.xinbo.springboot.backend.linkoria.app.shared.util.websocket;

/**
 * Wrapper genérico para asegurar que todos los mensajes tengan el mismo formato.
 */
public record WebSocketNotification<T>(
        String type,
        T payload,
        long timestamp
) {}
