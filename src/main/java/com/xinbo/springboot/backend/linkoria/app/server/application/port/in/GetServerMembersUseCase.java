package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.server.application.ServerMemberDetail;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;

import java.util.List;
import java.util.UUID;

public interface GetServerMembersUseCase {
    List<ServerMemberDetail> getMembers(GetMembersQuery query);
    record GetMembersQuery(UUID requesterId, Long serverId) {}
}
