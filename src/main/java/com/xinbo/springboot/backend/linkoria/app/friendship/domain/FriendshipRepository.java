package com.xinbo.springboot.backend.linkoria.app.friendship.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendshipRepository {
    Friendship save(Friendship friendship);

    List<Friendship> findByUserIdAndStatus(UUID id, FriendshipStatus status);

    Optional<Friendship> findBySenderReceiverId(UUID senderId, UUID receiverId);

    boolean existsByUsersAndStatusIn(UUID senderId, UUID receiverId, List<FriendshipStatus> statuses);
}
