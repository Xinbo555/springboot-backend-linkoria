package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface GetFriendshipsUseCase {

    List<GetFriendshipResponse> getFriends(UUID userId);

    List<GetFriendshipResponse> getFriendships(UUID userId);

    List<GetFriendshipResponse> getPendingReceived(UUID userId);

    List<GetFriendshipResponse> getPendingSent(UUID userId);

    record GetFriendshipResponse(
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
        public static GetFriendshipResponse from(Friendship friendship, UUID friendId, String friendUsername, String avatarUrl) {
            return new GetFriendshipResponse(
                    friendship.getId(),
                    friendship.getSenderId(),
                    friendship.getReceiverId(),
                    friendship.getStatus().name(),
                    friendId,
                    friendUsername,
                    avatarUrl,
                    friendship.getCreatedAt(),
                    friendship.getUpdatedAt()
            );
        }
    }
}
