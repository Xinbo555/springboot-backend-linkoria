package com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.controller;

import com.xinbo.springboot.backend.linkoria.app.shared.security.AuthenticatedUser;
import com.xinbo.springboot.backend.linkoria.app.typing.application.port.in.TypingUseCase;
import com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.dto.TypingAction;
import com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.dto.request.TypingWebSocketRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Controller
@Slf4j
public class TypingWebSocketController {

    private final TypingUseCase typingUseCase;

    public TypingWebSocketController(TypingUseCase typingUseCase) {
        this.typingUseCase = typingUseCase;
    }

    /**
     * Comando: Cliente notifica que está escribiendo o paró de escribir

     * Cliente envía a: /app/typing/{conversationId}
     * Payload: { "action": "START" } o { "action": "STOP" }

     * Respuesta se envía a: /topic/conversation/{conversationId}
     * Todos los clientes suscritos lo reciben automáticamente
     */
    @MessageMapping("/typing/{conversationId}")
    public void typing(
            @Payload TypingWebSocketRequest request,
            @DestinationVariable Long conversationId,
            Principal principal
    ) {
        AuthenticatedUser currentUser = (AuthenticatedUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        UUID userId = currentUser.getId();

        TypingUseCase.TypingCommand command = new TypingUseCase.TypingCommand(
                conversationId,
                userId
        );

        if (request.action() == TypingAction.START) {
            typingUseCase.startTyping(command);
        } else {
            typingUseCase.stopTyping(command);
        }

        log.debug("WebSocket: typing {} - conversationId: {}, userId: {}",
                request.action(), conversationId, userId);
    }
}