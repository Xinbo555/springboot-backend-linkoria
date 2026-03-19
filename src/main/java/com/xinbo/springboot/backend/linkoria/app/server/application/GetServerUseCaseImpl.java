package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.GetServerUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetServerUseCaseImpl implements GetServerUseCase {
    private final ServerRepository serverRepository;

    public GetServerUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Server getServer(GetServerQuery query) {
        Server server = serverRepository.findServerById(query.serverId()).orElseThrow(() -> new ResourceNotFoundException("No server found"));

        serverRepository.findMember(query.serverId(), query.requesterId())
                .orElseThrow(() -> new ResourceNotFoundException("requester user does not belong to the server"));

        return server;
    }
}
