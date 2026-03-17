package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.util.List;
import java.util.UUID;

public interface GetFriendshipsUseCase {

    List<Friendship> getFriends(UUID userId);

    List<Friendship> getPendingReceived(UUID userId);

    List<Friendship> getPendingSent(UUID userId);
}
