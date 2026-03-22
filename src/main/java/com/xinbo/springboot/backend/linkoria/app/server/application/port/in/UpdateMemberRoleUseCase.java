package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.server.application.ServerMemberDetail;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRole;

import java.util.UUID;

public interface UpdateMemberRoleUseCase {
    ServerMemberDetail update(UpdateRoleCommand command);

    record UpdateRoleCommand(UUID requesterId, UUID targetId, Long serverId, ServerRole newRole) {}
}
