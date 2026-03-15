package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.util.UUID;

public interface RemoveFriendUseCase {

    Friendship remove(RemoveCommand command);

    record RemoveCommand(UUID senderId, UUID receiverId) {}
}
