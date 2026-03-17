package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import java.util.UUID;

public interface KickMemberUseCase {
    void kick(KickCommand command);

    record KickCommand(UUID requesterId, UUID targetId, Long serverId) {}
}
