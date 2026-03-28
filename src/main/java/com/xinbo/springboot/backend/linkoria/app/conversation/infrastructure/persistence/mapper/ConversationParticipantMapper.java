package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipant;
import com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.entity.ConversationParticipantEntity;

public class ConversationParticipantMapper {

    private ConversationParticipantMapper() {}

    public static ConversationParticipant toDomain(ConversationParticipantEntity entity) {
        return ConversationParticipant.reconstitute(
                entity.getId(),
                entity.getConversationId(),
                entity.getUserId(),
                entity.getJoinedAt()
        );
    }

    public static ConversationParticipantEntity toEntity(ConversationParticipant domain) {
        return new ConversationParticipantEntity(
                domain.getId(),
                domain.getConversationId(),
                domain.getUserId(),
                domain.getJoinedAt()
        );
    }
}