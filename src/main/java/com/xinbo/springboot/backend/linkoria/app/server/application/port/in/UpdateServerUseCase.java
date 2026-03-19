package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;

import java.util.UUID;

public interface UpdateServerUseCase {
    Server update(UpdateCommand command);

    record UpdateCommand(Long serverId, UUID requesterId, String name, String iconUrl) {}
}
