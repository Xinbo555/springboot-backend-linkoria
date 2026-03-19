package com.xinbo.springboot.backend.linkoria.app.server.rest.dto.request;

import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRole;

public record UpdateMemberRoleRequest(ServerRole newRole) {
}
