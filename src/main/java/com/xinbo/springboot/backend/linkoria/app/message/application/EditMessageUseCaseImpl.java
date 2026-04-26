package com.xinbo.springboot.backend.linkoria.app.message.application;

import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.EditMessageUseCase;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageEditedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageEventPublisher;
import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.message.domain.repository.MessageRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.message.MessageContentException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.message.NotMessageAuthorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EditMessageUseCaseImpl implements EditMessageUseCase {

    private final MessageRepository messageRepository;
    private final MessageEventPublisher messageEventPublisher;

    private static final int MAX_MESSAGE_LENGTH = 2000;

    public EditMessageUseCaseImpl(MessageRepository messageRepository, MessageEventPublisher messageEventPublisher) {
        this.messageRepository = messageRepository;
        this.messageEventPublisher = messageEventPublisher;
    }

    @Override
    public EditMessageResponse execute(EditMessageCommand command) {

        Message originalMessage = messageRepository.findById(command.messageId())
                .orElseThrow(() -> new ResourceNotFoundException("Message not found: " + command.messageId()));

        if(originalMessage.isAuthor(command.userId())) {
            throw new NotMessageAuthorException("No tienes permiso para editar este mensaje");
        }

        validateMessageContent(command.newContent());

        Message editedMessage = originalMessage.edit(command.newContent());

        Message savedMessage = messageRepository.save(editedMessage);

        //publicamos el evento de dominio
        MessageEditedEvent event = MessageEditedEvent.from(originalMessage, savedMessage);

        messageEventPublisher.publish(event);

        return new EditMessageResponse(
                savedMessage.getId(),
                savedMessage.getConversationId(),
                savedMessage.getUserId(),
                savedMessage.getContent(),
                savedMessage.isEdited(),
                savedMessage.getUpdatedAt()
        );

    }

    private void validateMessageContent(String content) {
        if (content == null) {
            throw new MessageContentException("El contenido del mensaje no puede ser nulo");
        }

        String trimmed = content.trim();

        if (trimmed.isEmpty()) {
            throw new MessageContentException("El contenido del mensaje no puede estar vacío");
        }

        if (trimmed.length() > MAX_MESSAGE_LENGTH) {
            throw new MessageContentException(
                    "El contenido del mensaje excede el límite de " + MAX_MESSAGE_LENGTH + " caracteres"
            );
        }
    }
}
