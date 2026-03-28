package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetChannelConversationUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out.ChannelValidationPort;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetChannelConversationUseCaseImpl implements GetChannelConversationUseCase {
    private final ConversationRepository conversationRepository;
    private final ChannelValidationPort channelValidationPort;

    public GetChannelConversationUseCaseImpl(ConversationRepository conversationRepository, ChannelValidationPort channelValidationPort) {
        this.conversationRepository = conversationRepository;
        this.channelValidationPort = channelValidationPort;
    }

    @Override
    public Conversation execute(GetChannelConversationQuery query) {
        channelValidationPort.validateMember(query.requesterId(),query.channelId());
        return conversationRepository.findByChannelId(query.channelId()).orElseThrow(() -> new ResourceNotFoundException("Conversation not found for channel"));
    }
}
