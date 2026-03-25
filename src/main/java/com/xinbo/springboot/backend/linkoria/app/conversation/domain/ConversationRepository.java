package com.xinbo.springboot.backend.linkoria.app.conversation.domain;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository {
    Conversation save(Conversation conversation);

    Optional<Conversation> findById(Long id);

    List<Conversation> findAllByIdList(List<Long> Ids);

    Optional<Conversation> findByChannelId(Long id);
}
