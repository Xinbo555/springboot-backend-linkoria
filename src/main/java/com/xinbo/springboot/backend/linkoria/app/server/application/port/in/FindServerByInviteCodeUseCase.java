package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;

public interface FindServerByInviteCodeUseCase {
    Server find(FindCommand command);
    record FindCommand(String inviteCode) {}
}
