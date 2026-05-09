package com.xinbo.springboot.backend.linkoria.app.message.application;

import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.ConversationValidationPort;
import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.SendMessageUseCase;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageCreatedEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageEventPublisher;
import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.message.domain.model.MessageType;
import com.xinbo.springboot.backend.linkoria.app.message.domain.repository.MessageRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.message.MessageContentException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SendMessageUseCaseImpl implements SendMessageUseCase {

    private final MessageRepository messageRepository;
    private final MessageEventPublisher messageEventPublisher;
    private final ConversationValidationPort conversationValidationPort;

    private final static int MAX_MESSAGE_LENGTH = 2000;

    public SendMessageUseCaseImpl(MessageRepository messageRepository, MessageEventPublisher messageEventPublisher, ConversationValidationPort conversationValidationPort) {
        this.messageRepository = messageRepository;
        this.messageEventPublisher = messageEventPublisher;
        this.conversationValidationPort = conversationValidationPort;
    }

    @Override
    public SendMessageResponse execute(SendMessageCommand command) {

        conversationValidationPort.isUserParticipant(command.conversationId(), command.userId());

        validateMessageContent(command.content());

        MessageType messageType = parseMessageType(command.messageType());

        if (command.replyToMessageId() != null) {
            messageRepository.findById(command.replyToMessageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Mensaje al que se intenta responder no esxiste: " + command.replyToMessageId()));
        }

        Message message = Message.createNewWithReply(
                command.conversationId(),
                command.userId(),
                command.content(),
                messageType,
                command.replyToMessageId()
        );

        Message savedMessage = messageRepository.save(message);


        //Publicamos evento de dominio

        MessageCreatedEvent event = MessageCreatedEvent.from(savedMessage);

        messageEventPublisher.publish(event);

        return new SendMessageResponse(
                savedMessage.getId(),
                savedMessage.getConversationId(),
                savedMessage.getUserId(),
                savedMessage.getContent(),
                savedMessage.getMessageType().name(),
                savedMessage.isReply(),
                savedMessage.getCreatedAt()
        );

    }

    private MessageType parseMessageType(String messageTypeString) {
        if (messageTypeString == null || messageTypeString.trim().isEmpty()) {
            return MessageType.TEXT;
        }

        try {
            return MessageType.valueOf(messageTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return MessageType.TEXT;
        }
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
