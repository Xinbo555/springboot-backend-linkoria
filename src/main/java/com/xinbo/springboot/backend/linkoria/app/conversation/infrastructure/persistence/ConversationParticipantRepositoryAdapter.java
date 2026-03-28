package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipant;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipantRepository;
import com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.entity.ConversationParticipantEntity;
import com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.mapper.ConversationParticipantMapper;
import com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.repository.JpaConversationParticipantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ConversationParticipantRepositoryAdapter implements ConversationParticipantRepository {

    private final JpaConversationParticipantRepository jpaConversationParticipantRepository;

    public ConversationParticipantRepositoryAdapter(JpaConversationParticipantRepository jpaConversationParticipantRepository) {
        this.jpaConversationParticipantRepository = jpaConversationParticipantRepository;
    }

    @Override
    public ConversationParticipant save(ConversationParticipant conversationParticipant) {
        return ConversationParticipantMapper.toDomain(
                jpaConversationParticipantRepository.save(
                        ConversationParticipantMapper.toEntity(conversationParticipant)
                )
        );
    }

    @Override
    public List<ConversationParticipant> findByConversationId(Long id) {
        return jpaConversationParticipantRepository.findByConversationId(id).stream()
                .map(ConversationParticipantMapper::toDomain)
                .toList();
    }

    @Override
    public List<Long> findConversationIdsByUserId(UUID id) {
        return jpaConversationParticipantRepository.findByUserId(id).stream()
                .map(ConversationParticipantEntity::getConversationId)
                .toList();
    }

    @Override
    public boolean existsByConversationIdAndUserId(Long conversationId, UUID userId) {
        return jpaConversationParticipantRepository.existsByConversationIdAndUserId(conversationId, userId);
    }

    @Override
    public List<UUID> findUserIdsByConversationId(Long conversationId) {
        return jpaConversationParticipantRepository.findUserIdsByConversationId(conversationId);
    }
}