package com.xinbo.springboot.backend.linkoria.app.server.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.server.application.ServerMemberDetail;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;

import java.util.List;
import java.util.UUID;

public interface GetServersByUserIdUseCase {
    List<Server> getServers(GetServersQuery query);
    record GetServersQuery(UUID requesterId) {}
}
