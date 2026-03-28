package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetConversationByIdUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetConversationByIdUseCaseImpl implements GetConversationByIdUseCase {

    private final ConversationRepository conversationRepository;

    public GetConversationByIdUseCaseImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public Conversation execute(GetConversationByIdQuery query) {
        return conversationRepository.findById(query.conversationId())
                .orElseThrow(() -> new ResourceNotFoundException("No Conversation found"));
    }
}
