package com.xinbo.springboot.backend.linkoria.app.server.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServerRepository {
    Optional<Server> findServerById(Long serverId);

    List<ServerMember> findMembersByServerId(Long serverId);

    Server save(Server server);

    void deleteById(Long serverId);

    Optional<Server> findByInviteCode(String inviteCode);

    ServerMember saveMember(ServerMember member);

    void deleteMember(Long serverId, UUID userId);

    Optional<ServerMember> findMember(Long serverId, UUID userId);
}
