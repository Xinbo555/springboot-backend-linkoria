package com.xinbo.springboot.backend.linkoria.app.typing.application;

import com.xinbo.springboot.backend.linkoria.app.typing.application.port.in.TypingUseCase;
import com.xinbo.springboot.backend.linkoria.app.typing.infrastructure.websocket.TypingBroadcaster;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class TypingUseCaseImpl implements TypingUseCase {

    private final TypingBroadcaster typingBroadcaster;
    private final TaskScheduler taskScheduler;

    private final ConcurrentHashMap<String, ScheduledFuture<?>> typingTimers = new ConcurrentHashMap<>();

    private static final long TYPING_TIMEOUT_MS = 2000;

    public TypingUseCaseImpl(TypingBroadcaster typingBroadcaster, TaskScheduler taskScheduler) {
        this.typingBroadcaster = typingBroadcaster;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void startTyping(TypingCommand command) {
        String key = timerKey(command.conversationId(), command.userId());

        // Cancelar timer anterior si existía (el usuario sigue escribiendo)
        ScheduledFuture<?> existing = typingTimers.get(key);
        if (existing != null) existing.cancel(false);

        // Programar auto-STOP por si el cliente nunca envía señal de stop
        ScheduledFuture<?> future = taskScheduler.schedule(
                () -> {
                    typingBroadcaster.broadcastTypingStop(command.conversationId(), command.userId());
                    typingTimers.remove(key);
                    log.debug("Typing timeout expirado - conversationId: {}, userId: {}",
                            command.conversationId(), command.userId());
                },
                Instant.now().plusMillis(TYPING_TIMEOUT_MS)
        );

        typingTimers.put(key, future);
        typingBroadcaster.broadcastTypingStart(command.conversationId(), command.userId());
    }

    @Override
    public void stopTyping(TypingCommand command) {
        String key = timerKey(command.conversationId(), command.userId());

        // Cancelar el timer si el cliente envió STOP antes de que expirara
        ScheduledFuture<?> existing = typingTimers.remove(key);
        if (existing != null) existing.cancel(false);

        typingBroadcaster.broadcastTypingStop(command.conversationId(), command.userId());
    }

    private String timerKey(Long conversationId, UUID userId) {
        return conversationId + ":" + userId;
    }
}