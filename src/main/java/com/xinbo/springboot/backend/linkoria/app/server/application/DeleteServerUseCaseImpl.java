package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.DeleteServerUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.server.ServerUnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteServerUseCaseImpl implements DeleteServerUseCase {
    private final ServerRepository serverRepository;

    public DeleteServerUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void delete(DeleteCommand command) {
        serverRepository.findServerById(command.serverId())
                .orElseThrow(() -> new ResourceNotFoundException("No server found"));

        ServerMember requester = serverRepository.findMember(command.serverId(), command.requesterId())
                .orElseThrow(() -> new ResourceNotFoundException("No requester found"));

        if (!requester.isOwner()) {
            throw new ServerUnauthorizedException("The requester must to be owner");
        }

        //como la base de datos se va a eliminar on delete cascade no tengo que eliminar los members de uno en uno
        serverRepository.deleteById(command.serverId());
    }
}
