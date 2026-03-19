package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.LeaveServerUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.server.OwnerCannotLeaveException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LeaveServerUseCaseImpl implements LeaveServerUseCase {
    private final ServerRepository serverRepository;

    public LeaveServerUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void leave(LeaveCommand command) {
        serverRepository.findServerById(command.serverId()).orElseThrow(() -> new ResourceNotFoundException("No server found"));

        ServerMember member = serverRepository.findMember(command.serverId(), command.userId()).orElseThrow(() -> new ResourceNotFoundException("No member found"));

        if (member.isOwner()) {
            throw new OwnerCannotLeaveException("Transfer ownership before leaving");
        }

        serverRepository.deleteMember(command.serverId(), command.userId());
    }
}
