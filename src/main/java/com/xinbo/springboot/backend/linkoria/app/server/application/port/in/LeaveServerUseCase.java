package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import java.util.UUID;

public interface LeaveServerUseCase {
    void leave(LeaveCommand command);

    record LeaveCommand(UUID userId, Long serverId) {}
}
