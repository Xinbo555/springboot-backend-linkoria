package com.xinbo.springboot.backend.linkoria.app.friendship.rest.dto;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.time.Instant;
import java.util.UUID;

public record FriendshipResponse(
        Long id,
        UUID senderId,
        UUID receiverId,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
    public static FriendshipResponse from(Friendship friendship) {

        return new FriendshipResponse(
                friendship.getId(),
                friendship.getSenderId(),
                friendship.getReceiverId(),
                friendship.getStatus().toString(),
                friendship.getCreatedAt(),
                friendship.getUpdatedAt()
        );
    }
}

