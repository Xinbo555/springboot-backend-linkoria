package com.xinbo.springboot.backend.linkoria.app.server.application;

import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;

public record ServerMemberDetail(ServerMember member, String username, String avatarUrl) {
}
