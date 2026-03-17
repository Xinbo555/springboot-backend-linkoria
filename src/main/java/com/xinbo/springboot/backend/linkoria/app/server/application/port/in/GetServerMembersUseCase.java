package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;

import java.util.List;

public interface GetServerMembersUseCase {
    List<ServerMember> getMembers(GetMembersQuery query);
    record GetMembersQuery(Long serverId) {}
}
