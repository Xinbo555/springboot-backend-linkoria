package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaConversationRepository extends JpaRepository<ConversationEntity, Long> {

    Optional<ConversationEntity> findByChannelId(Long channelId);

    List<ConversationEntity> findAllByIdIn(List<Long> ids);

    @Query("""
        SELECT c FROM ConversationEntity c
        JOIN ConversationParticipantEntity p1 ON p1.conversationId = c.id AND p1.userId = :userA
        JOIN ConversationParticipantEntity p2 ON p2.conversationId = c.id AND p2.userId = :userB
        WHERE c.type = 'DM'
    """)
    Optional<ConversationEntity> findDmBetweenUsers(@Param("userA") UUID userA, @Param("userB") UUID userB);
}