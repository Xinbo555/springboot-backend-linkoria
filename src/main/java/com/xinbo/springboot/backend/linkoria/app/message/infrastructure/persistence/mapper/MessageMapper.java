package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.persistence.entity.MessageEntity;

public class MessageMapper {

    public static MessageEntity toEntity(Message message) {
        return new MessageEntity(
                message.getId(),
                message.getConversationId(),
                message.getUserId(),
                message.getContent(),
                message.getMessageType(),
                message.getReplyToMessageId(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }

    public static Message toDomain(MessageEntity entity) {
        return Message.reconstruct(
                entity.getId(),
                entity.getConversationId(),
                entity.getUserId(),
                entity.getContent(),
                entity.getMessageType(),
                entity.getReplyToMessageId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }


}
