package com.xinbo.springboot.backend.linkoria.app.conversation.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository {
    Conversation save(Conversation conversation);

    Optional<Conversation> findById(Long id);

    List<Conversation> findAllByIdList(List<Long> Ids);

    Optional<Conversation> findByChannelId(Long id);

    Optional<Conversation> findDmBetweenUsers(UUID userA, UUID userB);

    List<Conversation> findByUserId(UUID userId);
}
