package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gestor de sesiones WebSocket

 * Mantiene un registro de todas las sesiones activas, organizadas por conversación.
 * Permite:
 * - Registrar nuevas sesiones (cuando un usuario se conecta)
 * - Desregistrar sesiones (cuando un usuario se desconecta)
 * - Obtener todas las sesiones de una conversación (para broadcast)

 * Thread-safe usando ConcurrentHashMap y sincronización en operaciones críticas.

 * Estructura:
 * Map<conversationId, Set<WebSocketSession>>

 * Ejemplo:
 * - Conversación 1 → [Session1, Session2, Session3]
 * - Conversación 2 → [Session4, Session5]
 */
@Component
@Slf4j
public class WebSocketSessionManager {

    // Map thread-safe: conversationId -> Set de sesiones WebSocket activas
    private final Map<Long, Set<WebSocketSession>> conversationSessions = new ConcurrentHashMap<>();

    /**
     * Registra una nueva sesión para una conversación
     *
     * @param conversationId ID de la conversación
     * @param session Sesión WebSocket a registrar
     */
    public void registerSession(Long conversationId, WebSocketSession session) {
        log.debug("Registrando sesión WebSocket - conversationId: {}, sessionId: {}",
                conversationId, session.getId());

        // getOrDefault
        conversationSessions
                .computeIfAbsent(conversationId, k -> Collections.synchronizedSet(new HashSet<>()))
                .add(session);

        log.debug("Sesión registrada - conversationId: {}, totalSessions: {}",
                conversationId, conversationSessions.get(conversationId).size());
    }

    /**
     * Desregistra una sesión de todas las conversaciones
     *
     * @param session Sesión WebSocket a desregistrar
     */
    public void unregisterSession(WebSocketSession session) {
        log.debug("Desregistrando sesión WebSocket - sessionId: {}", session.getId());

        // Buscar y remover la sesión de todas las conversaciones
        conversationSessions.forEach((conversationId, sessions) -> {
            if (sessions.remove(session)) {
                log.debug("Sesión removida - conversationId: {}, remainingSessions: {}",
                        conversationId, sessions.size());

                //Limpiar conversación vacía
                if (sessions.isEmpty()) {
                    conversationSessions.remove(conversationId);
                    log.debug("Conversación limpiada (sin sesiones) - conversationId: {}", conversationId);
                }
            }
        });
    }

    /**
     * Obtiene todas las sesiones activas de una conversación
     *
     * @param conversationId ID de la conversación
     * @return Set de sesiones, vacío si no hay sesiones activas
     */
    public Set<WebSocketSession> getSessionsByConversation(Long conversationId) {
        Set<WebSocketSession> sessions = conversationSessions.get(conversationId);

        if(sessions == null) {
            return Collections.emptySet();
        }

        // Retornar una copia thread-safe para evitar ConcurrentModificationException
        return new HashSet<>(sessions);
    }

    /**
     * Verifica si hay sesiones activas en una conversación
     *
     * @param conversationId ID de la conversación
     * @return true si hay al menos una sesión activa
     */
    public boolean hasActiveSessions(Long conversationId) {
        Set<WebSocketSession> sessions = conversationSessions.get(conversationId);

        return sessions != null && !sessions.isEmpty();
    }

    /**
     * Obtiene el total de sesiones activas en toda la aplicación
     *
     * @return Cantidad total de sesiones
     */
    public int getTotalActiveSessions() {
        return conversationSessions.values()
                .stream()
                .mapToInt(Set::size)
                .sum();
    }

    /**
     * Obtiene el total de conversaciones con sesiones activas
     *
     * @return Cantidad de conversaciones
     */
    public int getActiveConversations() {
        return conversationSessions.size();
    }

    public void clearAll() {
        log.warn("Limpiando todas las sesiones WebSocket");
        conversationSessions.clear();
    }
}
