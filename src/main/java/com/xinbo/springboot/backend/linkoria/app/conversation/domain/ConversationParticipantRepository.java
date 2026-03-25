package com.xinbo.springboot.backend.linkoria.app.conversation.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationParticipantRepository {
    ConversationParticipant save(ConversationParticipant conversationParticipant);

    Optional<ConversationParticipant> findById(Long id);

    List<ConversationParticipant> findByConversationId(Long id);

    List<Long> findConversationIdsByUserId(UUID id);

    boolean existsByConversationIdAndUserId(Long conversationId, UUID userId);

    List<UUID> findUserIdsByConversationId(Long conversationId);

    void deleteByConversationIdAndUserId(Long conversationId, UUID userId);
}
