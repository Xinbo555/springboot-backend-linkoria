package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.GetServerMembersUseCase;
import com.xinbo.springboot.backend.linkoria.app.server.application.port.out.UserDataPort;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GetServerMembersUseCaseImpl implements GetServerMembersUseCase {
    private final ServerRepository serverRepository;
    private final UserDataPort userDataPort;

    public GetServerMembersUseCaseImpl(ServerRepository serverRepository, UserDataPort userDataPort) {
        this.serverRepository = serverRepository;
        this.userDataPort = userDataPort;
    }

    @Override
    public List<ServerMemberDetail> getMembers(GetMembersQuery query) {
        serverRepository.findServerById(query.serverId()).orElseThrow(() -> new ResourceNotFoundException("No server found"));

        serverRepository.findMember(query.serverId(), query.requesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found"));

        List<ServerMember> members = serverRepository.findMembersByServerId(query.serverId());
        List<UUID> userIds = members.stream().map(ServerMember::getUserId).toList();

        List<UserDataPort.UserData> userData = userDataPort.getUserDataBatch(userIds);

        Map<UUID, UserDataPort.UserData> userDataMap = userData.stream()
                .collect(Collectors.toMap(UserDataPort.UserData::id, u -> u));

        return members.stream()
                .map(member -> {
                    UserDataPort.UserData data = userDataMap.get(member.getUserId());
                    return new ServerMemberDetail(member, data.username(), data.avatarUrl());
                })
                .toList();
    }
}
