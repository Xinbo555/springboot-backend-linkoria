package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.UpdateServerUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.server.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateServerUseCaseImpl implements UpdateServerUseCase {
    private final ServerRepository serverRepository;

    public UpdateServerUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Server update(UpdateCommand command) {
        Server server = serverRepository.findServerById(command.serverId()).orElseThrow(() -> new ResourceNotFoundException("No server found"));

        ServerMember requester = serverRepository.findMember(command.serverId(), command.requester())
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found"));

        if (!requester.hasAdminPrivileges()) {
            throw new UnauthorizedException("Only owner or admin can update server");
        }

        if (command.name() != null) {
            server = server.updateName(command.name());
        }

        if (command.iconUrl() != null) {
            server = server.updateIcon(command.iconUrl());
        }

        return serverRepository.save(server);
    }
}
