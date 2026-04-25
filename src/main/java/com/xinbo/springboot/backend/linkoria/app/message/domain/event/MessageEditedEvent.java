package com.xinbo.springboot.backend.linkoria.app.message.domain.event;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class MessageEditedEvent implements MessageDomainEvent{
    private final Long messageId;
    private final Long conversationId;
    private final UUID userId;
    private final String previousContent;
    private final String newContent;
    private final Instant editedAt;
    private final Instant occurredAt;

    private MessageEditedEvent(Long messageId, Long conversationId, UUID userId,
                               String previousContent, String newContent,
                               Instant editedAt, Instant occurredAt) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.userId = userId;
        this.previousContent = previousContent;
        this.newContent = newContent;
        this.editedAt = editedAt;
        this.occurredAt = occurredAt;
    }

    public static MessageEditedEvent from(Message originalMessage, Message editedMessage) {
        Optional.ofNullable(originalMessage.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Original message must have an ID"));
        Optional.ofNullable(editedMessage.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Edited message must have an ID"));

        return new MessageEditedEvent(
                editedMessage.getId(),
                editedMessage.getConversationId(),
                editedMessage.getUserId(),
                originalMessage.getContent(),
                editedMessage.getContent(),
                editedMessage.getUpdatedAt(),
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

    public String getPreviousContent() {
        return previousContent;
    }

    public String getNewContent() {
        return newContent;
    }

    public Instant getEditedAt() {
        return editedAt;
    }

    @Override
    public Instant getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String toString() {
        return "MessageEditedEvent{" +
                "messageId=" + messageId +
                ", conversationId=" + conversationId +
                ", userId=" + userId +
                ", editedAt=" + editedAt +
                ", occurredAt=" + occurredAt +
                '}';
    }
}
