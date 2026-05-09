package com.xinbo.springboot.backend.linkoria.app.friendship.rest.dto;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.time.Instant;
import java.util.UUID;

public record FriendshipResponse(
        Long id,
        UUID senderId,
        UUID receiverId,
        String status,
        UUID friendId,
        String friendUsername,
        String avatarUrl,
        Instant createdAt,
        Instant updatedAt
) {
    public static FriendshipResponse from(Friendship friendship, UUID friendId, String friendUsername, String avatarUrl) {

        return new FriendshipResponse(
                friendship.getId(),
                friendship.getSenderId(),
                friendship.getReceiverId(),
                friendship.getStatus().toString(),
                friendId,
                friendUsername,
                avatarUrl,
                friendship.getCreatedAt(),
                friendship.getUpdatedAt()
        );
    }
}

