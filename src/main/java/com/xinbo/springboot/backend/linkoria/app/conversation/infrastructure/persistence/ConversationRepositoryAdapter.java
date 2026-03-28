package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence;

import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationRepository;
import com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.mapper.ConversationMapper;
import com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.repository.JpaConversationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ConversationRepositoryAdapter implements ConversationRepository {

    private final JpaConversationRepository jpaConversationRepository;

    public ConversationRepositoryAdapter(JpaConversationRepository jpaConversationRepository) {
        this.jpaConversationRepository = jpaConversationRepository;
    }

    @Override
    public Conversation save(Conversation conversation) {
        return ConversationMapper.toDomain(
                jpaConversationRepository.save(ConversationMapper.toEntity(conversation))
        );
    }

    @Override
    public Optional<Conversation> findById(Long id) {
        return jpaConversationRepository.findById(id)
                .map(ConversationMapper::toDomain);
    }

    @Override
    public List<Conversation> findAllByIdList(List<Long> ids) {
        return jpaConversationRepository.findAllByIdIn(ids).stream()
                .map(ConversationMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Conversation> findByChannelId(Long id) {
        return jpaConversationRepository.findByChannelId(id)
                .map(ConversationMapper::toDomain);
    }

    @Override
    public Optional<Conversation> findDmBetweenUsers(UUID userA, UUID userB) {
        return jpaConversationRepository.findDmBetweenUsers(userA, userB)
                .map(ConversationMapper::toDomain);
    }
}