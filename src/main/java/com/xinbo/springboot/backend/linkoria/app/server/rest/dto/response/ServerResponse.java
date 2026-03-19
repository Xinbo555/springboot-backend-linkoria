package com.xinbo.springboot.backend.linkoria.app.server.rest.dto.response;

import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;

public record ServerResponse(Long id, String name, String iconUrl, String inviteCode) {
    public static ServerResponse from(Server server) {
        return new ServerResponse(server.getId(), server.getName(), server.getIconUrl(), server.getInviteCode());
    }
}
