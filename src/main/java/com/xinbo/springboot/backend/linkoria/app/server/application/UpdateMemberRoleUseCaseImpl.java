package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.UpdateMemberRoleUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.application.port.out.UserDataPort;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRole;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.server.ServerUnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateMemberRoleUseCaseImpl implements UpdateMemberRoleUseCase {
    private final ServerRepository serverRepository;
    private final UserDataPort userDataPort;

    public UpdateMemberRoleUseCaseImpl(ServerRepository serverRepository, UserDataPort userDataPort) {
        this.serverRepository = serverRepository;
        this.userDataPort = userDataPort;
    }

    @Override
    public ServerMemberDetail update(UpdateRoleCommand command) {
        Server server = serverRepository.findServerById(command.serverId()).orElseThrow(() -> new ResourceNotFoundException("No server found"));

        ServerMember requester = serverRepository.findMember(command.serverId(), command.requesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found"));

        if (!requester.isOwner()) {
            throw new ServerUnauthorizedException("Only the owner can change roles");
        }

        ServerMember targetMember = serverRepository.findMember(command.serverId(), command.targetId())
                .orElseThrow(() -> new ResourceNotFoundException("No member found"));

        if (targetMember.isOwner() && command.newRole() != ServerRole.OWNER) {
            throw new ServerUnauthorizedException("Cannot degrade owner, transfer ownership instead");
        }

        if (command.newRole() == ServerRole.OWNER) {
            serverRepository.saveMember(requester.updateRole(ServerRole.ADMIN));
            serverRepository.save(server.updateOwner(command.targetId()));
        }

        ServerMember updatedMember = targetMember.updateRole(command.newRole());
        serverRepository.saveMember(updatedMember);

        UserDataPort.UserData userData = userDataPort.getUserData(command.targetId());
        return new ServerMemberDetail(updatedMember,userData.username(),userData.avatarUrl());
    }
}
