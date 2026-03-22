package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;

import java.util.Optional;
import java.util.UUID;

public interface GetServerUseCase {
    Server getServer(GetServerQuery query);

    record GetServerQuery(UUID requesterId, Long serverId) {}
}
