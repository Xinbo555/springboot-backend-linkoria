package com.xinbo.springboot.backend.linkoria.app.friendship.rest.dto;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;

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
    public static FriendshipResponse from(Friendship friendship, UUID currentUserId) {

        FriendshipStatus visibleStatus =
                friendship.getStatus() == FriendshipStatus.BLOCKED
                        && !friendship.getBlockedBy().equals(currentUserId)
                        ? FriendshipStatus.ACCEPTED
                        : friendship.getStatus();

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

