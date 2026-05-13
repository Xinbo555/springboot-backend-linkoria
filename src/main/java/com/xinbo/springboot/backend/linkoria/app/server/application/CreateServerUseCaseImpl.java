package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.CreateServerUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //transaccional porque si el saveMember falla, mi server se queda sin owner
public class CreateServerUseCaseImpl implements CreateServerUseCase {
    private final ServerRepository serverRepository;

    public CreateServerUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Server create(CreateServerCommand command) {
        Server createdServer = serverRepository.save(Server.create(command.serverName(), command.ownerId(), command.serverIcon()));

        //creamos un ServerMember con rol de OWNER en el servidor
        serverRepository.saveMember(ServerMember.create(createdServer.getId(), command.ownerId(), ServerRole.OWNER));

        return createdServer;
    }
}
