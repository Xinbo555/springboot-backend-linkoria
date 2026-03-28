package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.ValidateConversationAccessUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out.ChannelValidationPort;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipantRepository;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationRepository;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationType;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.conversation.ConversationUnauthorizedException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ValidateConversationAccessUseCaseImpl implements ValidateConversationAccessUseCase {

    private final ConversationParticipantRepository conversationParticipantRepository;
    private final ConversationRepository conversationRepository;
    private final ChannelValidationPort channelValidationPort;

    public ValidateConversationAccessUseCaseImpl(ConversationParticipantRepository conversationParticipantRepository, ConversationRepository conversationRepository, ChannelValidationPort channelValidationPort) {
        this.conversationParticipantRepository = conversationParticipantRepository;
        this.conversationRepository = conversationRepository;
        this.channelValidationPort = channelValidationPort;
    }

    @Override
    public void execute(ValidateConversationAccessCommand command) {
        Conversation conversation = conversationRepository.findById(command.conversationId())
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found"));

        if (conversation.getType() == ConversationType.DM) {
            if (!conversationParticipantRepository.existsByConversationIdAndUserId(command.conversationId(),command.userId())) {
                throw new ConversationUnauthorizedException("conversation access denied for current user");
            }
        } else if (conversation.getType() == ConversationType.CHANNEL) {
            channelValidationPort.validateMember(command.userId(), conversation.getChannelId());
        }
    }
}
