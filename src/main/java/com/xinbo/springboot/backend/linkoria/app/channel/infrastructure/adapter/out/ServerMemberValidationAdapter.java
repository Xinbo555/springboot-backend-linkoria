package com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.adapter.out;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRole;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.server.ServerUnauthorizedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServerMemberValidationAdapter implements ServerMemberValidationPort {

    private final ServerRepository serverRepository;

    public ServerMemberValidationAdapter(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void validateServerExists(Long serverId) {
        serverRepository.findServerById(serverId).orElseThrow(() -> new ResourceNotFoundException("No server found"));
    }

    @Override
    public void validateIsMember(UUID userId, Long serverId) {
        serverRepository.findMember(serverId, userId).orElseThrow(() -> new ResourceNotFoundException("No member found"));
    }

    @Override
    public void validateIsAdminOrOwner(UUID userId, Long serverId) {
        boolean isAdminOrOwner = serverRepository.findMember(serverId, userId)
                .map(m -> m.getRole() == ServerRole.OWNER || m.getRole() == ServerRole.ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("No member found"));

        if (!isAdminOrOwner) {
            throw new ServerUnauthorizedException("The role must be owner or admin");
        }
    }
}
