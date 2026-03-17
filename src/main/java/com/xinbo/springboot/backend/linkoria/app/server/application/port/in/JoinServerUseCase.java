package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;

import java.util.Optional;
import java.util.UUID;

public interface JoinServerUseCase {
    Server join(JoinCommand command);
    record JoinCommand(UUID userId, String inviteCode) {}
}
