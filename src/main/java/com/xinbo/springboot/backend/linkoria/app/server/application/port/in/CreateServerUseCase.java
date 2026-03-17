package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;

import java.util.UUID;

public interface CreateServerUseCase {
    Server create(CreateServerCommand command);
    record CreateServerCommand(UUID ownerId, String serverName) {}
}
