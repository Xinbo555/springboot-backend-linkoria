package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.FindServerByInviteCodeUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)  // <-- añade esto
public class FindServerByInviteCodeUseCaseImpl implements FindServerByInviteCodeUseCase {
    private final ServerRepository serverRepository;

    public FindServerByInviteCodeUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Server find(FindCommand command) {
        return serverRepository.findByInviteCode(command.inviteCode()).orElseThrow(() -> new ResourceNotFoundException("No invite code found"));
    }
}
