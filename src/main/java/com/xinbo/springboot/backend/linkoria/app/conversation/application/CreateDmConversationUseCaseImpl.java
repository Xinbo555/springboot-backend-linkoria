package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.CreateDmConversationUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out.UserValidationPort;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipant;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipantRepository;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CreateDmConversationUseCaseImpl implements CreateDmConversationUseCase {
    private final ConversationRepository conversationRepository;
    private final UserValidationPort userValidationPort;
    private final ConversationParticipantRepository conversationParticipantRepository;

    public CreateDmConversationUseCaseImpl(ConversationRepository conversationRepository, UserValidationPort userValidationPort, ConversationParticipantRepository conversationParticipantRepository) {
        this.conversationRepository = conversationRepository;
        this.userValidationPort = userValidationPort;
        this.conversationParticipantRepository = conversationParticipantRepository;
    }


    @Override
    public Conversation execute(CreateDmConversationCommand command) {
        userValidationPort.validateExists(command.targetId());

        Optional<Conversation> existing = conversationRepository.findDmBetweenUsers(command.requesterId(),command.targetId());
        if (existing.isPresent()) return existing.get();

        Conversation conversation = conversationRepository.save(Conversation.createDirect());
        conversationParticipantRepository.save(ConversationParticipant.create(conversation.getId(),command.requesterId()));
        conversationParticipantRepository.save(ConversationParticipant.create(conversation.getId(),command.targetId()));
        return conversation;
    }
}
