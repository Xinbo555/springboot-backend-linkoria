package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetConversationResponse;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetConversationsByUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out.ConversationUserDataPort;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationParticipantRepository;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.ConversationRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GetConversationsByUserUseCaseImpl implements GetConversationsByUserUseCase {

    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository conversationParticipantRepository;
    private final ConversationUserDataPort conversationUserDataPort;

    public GetConversationsByUserUseCaseImpl(ConversationRepository conversationRepository, ConversationParticipantRepository conversationParticipantRepository, ConversationUserDataPort conversationUserDataPort) {
        this.conversationRepository = conversationRepository;
        this.conversationParticipantRepository = conversationParticipantRepository;
        this.conversationUserDataPort = conversationUserDataPort;
    }

    @Override
    public List<GetConversationResponse> execute(GetConversationsByUserQuery query) {
        List<Long> conversationIds = conversationParticipantRepository.findConversationIdsByUserId(query.requesterId());

        List<Conversation> conversations = conversationRepository.findAllByIdList(conversationIds);
        return conversations.stream()
                .map(conversation -> {
                    UUID targetId = conversationParticipantRepository.findUserIdsByConversationId(conversation.getId())
                            .stream().filter(uuid -> !uuid.equals(query.requesterId())).findAny().orElseThrow(() -> new ResourceNotFoundException("como has llegado aquí? (estas solo en la conversación)"));

                    ConversationUserDataPort.ConversationUserData conversationUserData = conversationUserDataPort.getUserData(targetId);
                    return GetConversationResponse.from(conversation, conversationUserData.id(), conversationUserData.username(), conversationUserData.avatarUrl());
                }).toList();
    }
}
