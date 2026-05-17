package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.persistence;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.message.domain.repository.MessageRepository;
import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.persistence.entity.MessageEntity;
import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.persistence.mapper.MessageMapper;
import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.persistence.repository.JpaMessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepositoryAdapter implements MessageRepository {

    private final JpaMessageRepository jpaMessageRepository;

    public MessageRepositoryAdapter(JpaMessageRepository jpaMessageRepository) {
        this.jpaMessageRepository = jpaMessageRepository;
    }

    @Override
    public Message save(Message message) {
        MessageEntity entity = MessageMapper.toEntity(message);
        MessageEntity savedEntity = jpaMessageRepository.save(entity);
        return MessageMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Message> findById(Long messageId) {
        Optional<MessageEntity> entity = jpaMessageRepository.findById(messageId);
        return entity.map(MessageMapper::toDomain);
    }

    @Override
    public List<Message> findByConversationIdCursorPaginated(Long conversationId, Long cursor, int limit, PaginationDirection direction) {
        List<MessageEntity> entities = direction == PaginationDirection.BACKWARDS
                ? jpaMessageRepository.findBackwards(conversationId, cursor, limit)
                : jpaMessageRepository.findForwards(conversationId, cursor, limit);
        return entities.stream().map(MessageMapper::toDomain).toList();
    }

    @Override
    public Long countByConversationId(Long conversationId) {
        return jpaMessageRepository.countByConversationId(conversationId);
    }

    @Override
    public Long deleteByConversationId(Long conversationId) {
        return jpaMessageRepository.deleteByConversationId(conversationId);
    }

    @Override
    public Optional<Message> findLastMessageInConversation(Long conversationId) {
        return jpaMessageRepository.findLastMessageInConversation(conversationId)
                .map(MessageMapper::toDomain);
    }

    @Override
    public void deleteById(Long messageId) {
        jpaMessageRepository.deleteById(messageId);
    }
}
