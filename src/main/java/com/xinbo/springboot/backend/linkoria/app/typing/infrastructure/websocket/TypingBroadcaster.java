package com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket;

import com.xinbo.springboot.backend.linkoria.app.shared.util.websocket.WebSocketNotification;
import com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.dto.TypingAction;
import com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.dto.response.TypingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@Slf4j
public class TypingBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public TypingBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastTypingStart(Long conversationId, UUID userId) {
        broadcast(conversationId, userId, TypingAction.START);
    }

    public void broadcastTypingStop(Long conversationId, UUID userId) {
        broadcast(conversationId, userId, TypingAction.STOP);
    }

    private void broadcast(Long conversationId, UUID userId, TypingAction action) {
        TypingResponse payload = new TypingResponse(userId, conversationId, action);

        WebSocketNotification<TypingResponse> notification =
                new WebSocketNotification<>(
                        "TYPING_" + action.name(),
                        payload,
                        Instant.now().toEpochMilli()
                );

        String destination = "/topic/conversation/" + conversationId;
        messagingTemplate.convertAndSend(destination, notification);

        log.debug("Typing {} enviado a {}, userId: {}", action, destination, userId);
    }
}
