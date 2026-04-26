package com.xinbo.springboot.backend.linkoria.app.message.domain.model;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.message.NonEditableMessageTypeException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Message {

    private final Long id;
    private final Long conversationId;
    private final UUID userId;
    private final String content;
    private final MessageType messageType;
    private final Long replyToMessageId;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Message(Long id, Long conversationId, UUID userId, String content,
                    MessageType messageType, Long replyToMessageId,
                    Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.userId = userId;
        this.content = content;
        this.messageType = messageType;
        this.replyToMessageId = replyToMessageId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Message createNew(Long conversationId, UUID userId, String content, MessageType messageType) {
        return new Message(null, conversationId, userId, content, messageType, null, Instant.now(), Instant.now());
    }

    public static Message createNewWithReply(Long conversationId, UUID userId, String content,
                                             MessageType messageType, Long replyToMessageId) {
        return new Message(null, conversationId, userId, content, messageType,
                replyToMessageId, Instant.now(), Instant.now());
    }

    public static Message reconstruct(Long id, Long conversationId, UUID userId,
                                      String content, MessageType messageType,
                                      Long replyToMessageId, Instant createdAt,
                                      Instant updatedAt) {
        return new Message(id, conversationId, userId, content, messageType,
                replyToMessageId, createdAt, updatedAt);
    }

    public Message edit(String newContent) {
        if(this.messageType != MessageType.TEXT) {
            throw new NonEditableMessageTypeException("only text-messages can be edited");
        }
        return new Message(this.id, this.conversationId, this.userId,
                newContent, this.messageType, this.replyToMessageId,
                this.createdAt, Instant.now());
    }

    public boolean isEdited() {
        return !this.createdAt.equals(this.updatedAt);
    }

    public boolean isReply() {
        return this.replyToMessageId != null;
    }

    public boolean isAuthor(UUID userId) {
        return this.userId.equals(userId);
    }

    public String getContent() {
        return content;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Long getReplyToMessageId() {
        return replyToMessageId;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", conversationId=" + conversationId +
                ", userId=" + userId +
                ", messageType=" + messageType +
                ", isEdited=" + isEdited() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
