package com.xinbo.springboot.backend.linkoria.app.conversation.application;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.CreateChannelConversationUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.FindParticipantsByConversationIdUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetConversationByIdUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.ValidateConversationAccessUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.domain.Conversation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ConversationFacade {

    private final GetConversationByIdUseCase getConversationById;
    private final ValidateConversationAccessUseCase validateConversationAccess;
    private final FindParticipantsByConversationIdUseCase findParticipants;
    private final CreateChannelConversationUseCase createChannelConversation;

    public ConversationFacade(GetConversationByIdUseCase getConversationById, ValidateConversationAccessUseCase validateConversationAccess, FindParticipantsByConversationIdUseCase findParticipants, CreateChannelConversationUseCase createChannelConversation) {
        this.getConversationById = getConversationById;
        this.validateConversationAccess = validateConversationAccess;
        this.findParticipants = findParticipants;
        this.createChannelConversation = createChannelConversation;
    }

    public Conversation getById(Long conversationId) {
        return getConversationById.execute(
                new GetConversationByIdUseCase.GetConversationByIdQuery(conversationId)
        );
    }

    public void validateExists(Long conversationId) {
        getConversationById.execute(
                new GetConversationByIdUseCase.GetConversationByIdQuery(conversationId)
        );
    }

    public void validateAccess(Long conversationId, UUID userId) {
        validateConversationAccess.execute(
                new ValidateConversationAccessUseCase.ValidateConversationAccessCommand(conversationId, userId)
        );
    }

    public List<UUID> findParticipantIds(Long conversationId) {
        return findParticipants.execute(
                new FindParticipantsByConversationIdUseCase.FindParticipantsQuery(conversationId)
        );
    }

    public Conversation createForChannel(Long channelId) {
        return createChannelConversation.execute(
                new CreateChannelConversationUseCase.CreateChannelConversationCommand(channelId)
        );
    }
}
