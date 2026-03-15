package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.util.UUID;

public interface DeclineFriendRequestUseCase {

    Friendship decline(DeclineCommand command);

    record DeclineCommand(UUID senderId, UUID receiverId) {}
}
