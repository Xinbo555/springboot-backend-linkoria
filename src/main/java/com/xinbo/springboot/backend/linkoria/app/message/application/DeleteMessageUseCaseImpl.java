package com.xinbo.springboot.backend.linkoria.app.message.application;

import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.DeleteMessageUseCase;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageDeletedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageEventPublisher;
import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.message.domain.repository.MessageRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.message.NotMessageAuthorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteMessageUseCaseImpl implements DeleteMessageUseCase {

    private final MessageRepository messageRepository;
    private final MessageEventPublisher messageEventPublisher;

    public DeleteMessageUseCaseImpl(MessageRepository messageRepository, MessageEventPublisher messageEventPublisher) {
        this.messageRepository = messageRepository;
        this.messageEventPublisher = messageEventPublisher;
    }

    @Override
    public DeleteMessageResponse execute(DeleteMessageCommand command) {

        Message message = messageRepository.findById(command.messageId())
                .orElseThrow(() -> new ResourceNotFoundException("Message not found" + command.messageId()));

        if(!message.isAuthor(command.userId())) {
            throw new NotMessageAuthorException("No tienes permiso para eliminar este mensaje");
        }

        messageRepository.deleteById(command.messageId());

        //Publicamos evento de dominio
        MessageDeletedEvent event = MessageDeletedEvent.from(message);
        messageEventPublisher.publish(event);


        return new DeleteMessageResponse(
                command.messageId(),
                message.getConversationId(),
                true
        );
    }
}
