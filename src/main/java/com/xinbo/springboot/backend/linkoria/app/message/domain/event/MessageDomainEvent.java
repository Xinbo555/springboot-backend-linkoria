package com.xinbo.springboot.backend.linkoria.app.message.domain.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Interfaz base para todos los eventos de dominio del módulo message.
 */
public interface MessageDomainEvent {
    Long getMessageId();
    Long getConversationId();
    UUID getUserId();
    Instant getOccurredAt();
}