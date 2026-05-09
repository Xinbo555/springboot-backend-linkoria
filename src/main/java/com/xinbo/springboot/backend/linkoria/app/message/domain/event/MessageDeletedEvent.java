package com.xinbo.springboot.backend.linkoria.app.message.domain.event;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class MessageDeletedEvent implements MessageDomainEvent{

    private final Long messageId;
    private final Long conversationId;
    private final UUID userId;
    private final Instant deletedAt;
    private final Instant occurredAt;

    private MessageDeletedEvent(Long messageId, Long conversationId, UUID userId,
                                Instant deletedAt, Instant occurredAt) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.userId = userId;
        this.deletedAt = deletedAt;
        this.occurredAt = occurredAt;
    }

    public static MessageDeletedEvent from(Message message) {
        Optional.ofNullable(message.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Message ID cannot be null"));
        return new MessageDeletedEvent(
                message.getId(),
                message.getConversationId(),
                message.getUserId(),
                Instant.now(),
                Instant.now()
        );
    }

    @Override
    public Long getMessageId() {
        return messageId;
    }

    @Override
    public Long getConversationId() {
        return conversationId;
    }

    @Override
    public UUID getUserId() {
        return userId;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Instant getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String toString() {
        return "MessageDeletedEvent{" +
                "messageId=" + messageId +
                ", conversationId=" + conversationId +
                ", userId=" + userId +
                ", deletedAt=" + deletedAt +
                ", occurredAt=" + occurredAt +
                '}';
    }
}
