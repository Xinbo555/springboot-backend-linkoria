package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.UpdateMemberRoleUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRole;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.server.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateMemberRoleUseCaseImpl implements UpdateMemberRoleUseCase {
    private final ServerRepository serverRepository;

    public UpdateMemberRoleUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public ServerMember update(UpdateRoleCommand command) {
        Server server = serverRepository.findServerById(command.serverId()).orElseThrow(() -> new ResourceNotFoundException("No server found"));

        ServerMember requester = serverRepository.findMember(command.serverId(), command.requesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found"));

        if (!requester.isOwner()) {
            throw new UnauthorizedException("Only the owner can change roles");
        }

        ServerMember targetMember = serverRepository.findMember(command.serverId(), command.targetId())
                .orElseThrow(() -> new ResourceNotFoundException("No member found"));

        if (targetMember.isOwner() && command.newRole() != ServerRole.OWNER) {
            throw new UnauthorizedException("Cannot degrade owner, transfer ownership instead");
        }

        if (command.newRole() == ServerRole.OWNER) {
            serverRepository.saveMember(requester.updateRole(ServerRole.ADMIN));
            serverRepository.save(server.updateOwner(command.targetId()));
        }

        ServerMember updatedMember = targetMember.updateRole(command.newRole());
        serverRepository.saveMember(updatedMember);
        return updatedMember;
    }
}
