package com.xinbo.springboot.backend.linkoria.app.friendship.rest.dto;

import java.util.UUID;

public record FriendshipActionRequest(UUID targetId) {
}