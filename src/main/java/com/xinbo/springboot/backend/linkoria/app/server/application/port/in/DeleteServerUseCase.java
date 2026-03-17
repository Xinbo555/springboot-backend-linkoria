package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import java.util.UUID;

public interface DeleteServerUseCase {
    void delete(DeleteCommand command);

    record DeleteCommand(UUID requesterId, Long serverId) {}
}
