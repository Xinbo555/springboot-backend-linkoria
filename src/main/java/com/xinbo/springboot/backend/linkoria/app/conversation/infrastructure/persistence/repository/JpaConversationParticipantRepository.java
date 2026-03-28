package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.persistence.entity.ConversationParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaConversationParticipantRepository extends JpaRepository<ConversationParticipantEntity, Long> {

    List<ConversationParticipantEntity> findByConversationId(Long conversationId);

    List<ConversationParticipantEntity> findByUserId(UUID userId);

    boolean existsByConversationIdAndUserId(Long conversationId, UUID userId);

    @Query("SELECT p.userId FROM ConversationParticipantEntity p WHERE p.conversationId = :conversationId")
    List<UUID> findUserIdsByConversationId(@Param("conversationId") Long conversationId);

    void deleteByConversationIdAndUserId(Long conversationId, UUID userId);
}