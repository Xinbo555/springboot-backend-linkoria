package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.FindParticipantsByConversationIdUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipant;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FindParticipantsByConversationIdUseCaseImpl implements FindParticipantsByConversationIdUseCase {
    private final ConversationParticipantRepository conversationParticipantRepository;

    public FindParticipantsByConversationIdUseCaseImpl(ConversationParticipantRepository conversationParticipantRepository) {
        this.conversationParticipantRepository = conversationParticipantRepository;
    }

    @Override
    public List<UUID> execute(FindParticipantsQuery query) {
        return conversationParticipantRepository.findByConversationId(query.conversationId()).stream().map(ConversationParticipant::getUserId).toList();
    }
}
