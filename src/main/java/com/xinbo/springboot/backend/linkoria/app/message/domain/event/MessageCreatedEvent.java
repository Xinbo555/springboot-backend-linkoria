package com.xinbo.springboot.backend.linkoria.app.message.domain.event;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.message.domain.model.MessageType;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class MessageCreatedEvent implements MessageDomainEvent{

    private final Long messageId;
    private final Long conversationId;
    private final UUID userId;
    private final String content;
    private final MessageType messageType;
    private final Long replyToMessageId;
    private final Instant createdAt;
    private final Instant occurredAt;

    private MessageCreatedEvent(Long messageId, Long conversationId, UUID userId,
                                String content, MessageType messageType,
                                Long replyToMessageId, Instant createdAt, Instant occurredAt) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.userId = userId;
        this.content = content;
        this.messageType = messageType;
        this.replyToMessageId = replyToMessageId;
        this.createdAt = createdAt;
        this.occurredAt = occurredAt;
    }

    public static MessageCreatedEvent from(Message message) {
        Optional.ofNullable(message.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Message ID cannot be null"));
        return new MessageCreatedEvent(
                message.getId(),
                message.getConversationId(),
                message.getUserId(),
                message.getContent(),
                message.getMessageType(),
                message.getReplyToMessageId(),
                message.getCreatedAt(),
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

    public String getContent() {
        return content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Long getReplyToMessageId() {
        return replyToMessageId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Instant getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String toString() {
        return "MessageCreatedEvent{" +
                "messageId=" + messageId +
                ", conversationId=" + conversationId +
                ", userId=" + userId +
                ", messageType=" + messageType +
                ", occurredAt=" + occurredAt +
                '}';
    }
}
