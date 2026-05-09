package com.xinbo.springboot.backend.linkoria.app.friendship.rest.dto;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.GetFriendshipsUseCase;
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
    public static FriendshipResponse from(GetFriendshipsUseCase.GetFriendshipResponse getFriendshipResponse) {

        return new FriendshipResponse(
                getFriendshipResponse.id(),
                getFriendshipResponse.senderId(),
                getFriendshipResponse.receiverId(),
                getFriendshipResponse.status(),
                getFriendshipResponse.friendId(),
                getFriendshipResponse.friendUsername(),
                getFriendshipResponse.avatarUrl(),
                getFriendshipResponse.createdAt(),
                getFriendshipResponse.updatedAt()
        );
    }
}

