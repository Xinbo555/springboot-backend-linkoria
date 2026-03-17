package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.JoinServerUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRole;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.server.AlreadyMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JoinServerUseCaseImpl implements JoinServerUseCase {
    private final ServerRepository serverRepository;

    public JoinServerUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Server join(JoinCommand command) {
        Server server = serverRepository.findByInviteCode(command.inviteCode()).orElseThrow(() -> new ResourceNotFoundException("No invite code found"));

        serverRepository.findMember(server.getId(), command.userId()).ifPresent(m -> {
            throw new AlreadyMemberException("Member is already in the server");
        });

        serverRepository.saveMember(ServerMember.create(server.getId(), command.userId(), ServerRole.MEMBER));

        return server;
    }
}
