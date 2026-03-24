package com.xinbo.springboot.backend.linkoria.app.channel.application.port.out;

import java.util.UUID;

public interface ServerMemberValidationPort {
    void validateServerExists(Long serverId);
    void validateIsMember(UUID userId, Long serverId);
    void validateIsAdminOrOwner(UUID userId, Long serverId);
}
