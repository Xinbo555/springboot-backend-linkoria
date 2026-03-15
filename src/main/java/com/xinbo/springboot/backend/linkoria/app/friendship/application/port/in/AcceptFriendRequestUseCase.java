package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.util.UUID;

public interface AcceptFriendRequestUseCase {

    Friendship accept(AcceptCommand command);

    record AcceptCommand(UUID senderId, UUID receiverId) {}
}
