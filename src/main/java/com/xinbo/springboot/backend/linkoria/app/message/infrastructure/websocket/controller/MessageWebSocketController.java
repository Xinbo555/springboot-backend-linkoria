package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.controller;

import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.DeleteMessageUseCase;
import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.EditMessageUseCase;
import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.SendMessageUseCase;
import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.dto.request.DeleteMessageWebSocketRequest;
import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.dto.request.EditMessageWebSocketRequest;
import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.websocket.dto.request.SendMessageWebSocketRequest;
import com.xinbo.springboot.backend.linkoria.app.shared.security.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.UUID;


/**
 * Controller WebSocket STOMP para mensajes

 * Maneja comandos STOMP enviados por clientes:
 * - SEND /app/message/send → Crear mensaje
 * - SEND /app/message/edit → Editar mensaje
 * - SEND /app/message/delete → Eliminar mensaje

 * Los clientes se suscriben a:
 * - SUBSCRIBE /topic/conversation/{conversationId}

 * Las respuestas son automáticamente enviadas por STOMP
 * a todos los clientes suscritos en ese tópico.
 */
@Controller
@Slf4j
public class MessageWebSocketController {

    private final SendMessageUseCase sendMessageUseCase;
    private final EditMessageUseCase editMessageUseCase;
    private final DeleteMessageUseCase deleteMessageUseCase;

    public MessageWebSocketController(SendMessageUseCase sendMessageUseCase, EditMessageUseCase editMessageUseCase, DeleteMessageUseCase deleteMessageUseCase) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.editMessageUseCase = editMessageUseCase;
        this.deleteMessageUseCase = deleteMessageUseCase;
    }

    /**
     * Comando: Cliente envía mensaje

     * Cliente envía a: /app/message/send/{conversationId}
     * Payload: { "content": "Hola", "messageType": "TEXT", "replyToMessageId": null }

     * Respuesta se envía a: /topic/conversation/{conversationId}
     * Todos los clientes suscritos lo reciben automáticamente
     */
    @MessageMapping("/message/send/{conversationId}")
    public void sendMessage(
            @Payload SendMessageWebSocketRequest request,
            @DestinationVariable Long conversationId,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {

        UUID userId = currentUser.getId();

        //Ejecutar caso de uso
        SendMessageUseCase.SendMessageCommand command = new SendMessageUseCase.SendMessageCommand(
                conversationId,
                userId,
                request.content(),
                request.messageType(),
                request.replyToMessageId()
        );

        SendMessageUseCase.SendMessageResponse response = sendMessageUseCase.execute(command);

        log.debug("WebSocket: Mensaje enviado - conversationId: {}, userId: {}",
                conversationId, userId);
    }

    /**
     * Comando: Cliente edita mensaje

     * Cliente envía a: /app/message/edit/{conversationId}
     * Payload: { "messageId": 456, "newContent": "Contenido editado" }

     * Respuesta se envía a: /topic/conversation/{conversationId}
     * Todos los clientes suscritos lo reciben automáticamente
     */
    @MessageMapping("/message/edit/{conversationId}")
    public void editMessage(
            @Payload EditMessageWebSocketRequest request,
            @DestinationVariable Long conversationId,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        UUID userId = currentUser.getId();

        EditMessageUseCase.EditMessageCommand command = new EditMessageUseCase.EditMessageCommand(
                request.messageId(),
                userId,
                request.newContent()
        );

        EditMessageUseCase.EditMessageResponse response = editMessageUseCase.execute(command);

        log.debug("WebSocket: mensaje editado - messageId: {}, userId: {}",
                request.messageId(), userId);
    }

    /**
     * Comando: Cliente elimina mensaje

     * Cliente envía a: /app/message/delete/{conversationId}
     * Payload: { "messageId": 456 }

     * Respuesta se envía a: /topic/conversation/{conversationId}
     * Todos los clientes suscritos lo reciben automáticamente
     */
    @MessageMapping("/message/delete/{conversationId}")
    public void deleteMessage(
            @Payload DeleteMessageWebSocketRequest request,
            @DestinationVariable Long conversationId,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        UUID userId = currentUser.getId();

        DeleteMessageUseCase.DeleteMessageCommand command = new DeleteMessageUseCase.DeleteMessageCommand(
                request.messageId(),
                userId
        );

        DeleteMessageUseCase.DeleteMessageResponse response = deleteMessageUseCase.execute(command);

        log.debug("WebSocket: mensaje eliminado - messageId: {}, userId: {}",
                request.messageId(), userId);
    }
}
