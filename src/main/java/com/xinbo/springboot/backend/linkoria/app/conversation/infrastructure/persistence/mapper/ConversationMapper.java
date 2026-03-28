package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.entity.ConversationEntity;

public class ConversationMapper {

    private ConversationMapper() {}

    public static Conversation toDomain(ConversationEntity entity) {
        return Conversation.reconstitute(
                entity.getId(),
                entity.getType(),
                entity.getChannelId(),
                entity.getCreatedAt()
        );
    }

    public static ConversationEntity toEntity(Conversation domain) {
        return new ConversationEntity(
                domain.getId(),
                domain.getType(),
                domain.getChannelId(),
                domain.getCreatedAt()
        );
    }
}