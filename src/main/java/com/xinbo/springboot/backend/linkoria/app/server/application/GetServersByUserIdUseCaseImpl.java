package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.GetServersByUserIdUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetServersByUserIdUseCaseImpl implements GetServersByUserIdUseCase {
    private final ServerRepository serverRepository;

    public GetServersByUserIdUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public List<Server> getServers(GetServersQuery query) {
        return serverRepository.findServersByUserId(query.requesterId());
    }
}
