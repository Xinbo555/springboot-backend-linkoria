package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetConversationsByUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipantRepository;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetConversationsByUserUseCaseImpl implements GetConversationsByUserUseCase {

    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository conversationParticipantRepository;

    public GetConversationsByUserUseCaseImpl(ConversationRepository conversationRepository, ConversationParticipantRepository conversationParticipantRepository) {
        this.conversationRepository = conversationRepository;
        this.conversationParticipantRepository = conversationParticipantRepository;
    }

    @Override
    public List<Conversation> execute(GetConversationsByUserQuery query) {
        List<Long> conversationIds = conversationParticipantRepository.findConversationIdsByUserId(query.requesterId());
        return conversationRepository.findAllByIdList(conversationIds);
    }
}
