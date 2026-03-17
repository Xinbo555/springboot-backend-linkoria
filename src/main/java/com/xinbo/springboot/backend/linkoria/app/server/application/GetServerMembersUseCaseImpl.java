package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.GetServerMembersUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetServerMembersUseCaseImpl implements GetServerMembersUseCase {
    private final ServerRepository serverRepository;

    public GetServerMembersUseCaseImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public List<ServerMember> getMembers(GetMembersQuery query) {
        serverRepository.findServerById(query.serverId()).orElseThrow(() -> new ResourceNotFoundException("No server found"));

        return serverRepository.findMembersByServerId(query.serverId());
    }
}
