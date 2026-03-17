package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.KickMemberUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.server.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KickMemberUseCaseImpl implements KickMemberUseCase {
    private final ServerRepository serverRepository;

    public KickMemberUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void kick(KickCommand command) {
        serverRepository.findServerById(command.serverId()).orElseThrow(() -> new ResourceNotFoundException("No server found"));

        ServerMember requester = serverRepository.findMember(command.serverId(), command.requesterId())
                .orElseThrow(() -> new ResourceNotFoundException("requester user does not belong to the server"));

        ServerMember target = serverRepository.findMember(command.serverId(), command.targetId())
                .orElseThrow(() -> new ResourceNotFoundException("target user does not belong to the server"));

        if (!requester.hasAdminPrivileges()) {
            throw new UnauthorizedException("requester must has admin privilegies");
        }

        if (target.isOwner()) {
            throw new UnauthorizedException("The owner can not be kicked");
        }

        serverRepository.deleteMember(command.serverId(), command.targetId());
    }
}
