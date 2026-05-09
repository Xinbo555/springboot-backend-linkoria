package com.xinbo.springboot.backend.linkoria.app.message.application;

import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.GetLastMessageUseCase;
import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.message.domain.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GetLastMessageUseCaseImpl implements GetLastMessageUseCase {

    private final MessageRepository messageRepository;

    public GetLastMessageUseCaseImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public GetLastMessageResponse execute(GetLastMessageQuery query) {
        Optional<Message> lastMessage = messageRepository.findLastMessageInConversation(query.conversationId());

        if(lastMessage.isEmpty()) {
            return new GetLastMessageResponse(null);
        }

        Message message = lastMessage.get();

        return new GetLastMessageResponse(message);
    }
}
