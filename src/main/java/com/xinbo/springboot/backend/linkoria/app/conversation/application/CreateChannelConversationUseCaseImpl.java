package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.CreateChannelConversationUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out.ChannelValidationPort;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateChannelConversationUseCaseImpl implements CreateChannelConversationUseCase {

    private final ConversationRepository conversationRepository;

    public CreateChannelConversationUseCaseImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public Conversation execute(CreateChannelConversationCommand command) {
        return conversationRepository.save(Conversation.createChannel(command.channelId()));
    }
}
