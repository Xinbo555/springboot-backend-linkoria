package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetDmConversationUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out.UserValidationPort;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipantRepository;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetDmConversationUseCaseImpl implements GetDmConversationUseCase {

    private final UserValidationPort userValidationPort;
    private final ConversationRepository conversationRepository;

    public GetDmConversationUseCaseImpl(UserValidationPort userValidationPort, ConversationRepository conversationRepository) {
        this.userValidationPort = userValidationPort;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public Conversation execute(GetDmConversationQuery query) {
        userValidationPort.validateExists(query.targetId());

        return conversationRepository.findDmBetweenUsers(query.targetId(),query.requesterId())
                .orElseThrow(() -> new ResourceNotFoundException("No conversation between these two users found"));
    }
}
