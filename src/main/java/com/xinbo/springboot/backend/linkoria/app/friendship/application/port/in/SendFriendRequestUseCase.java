package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.util.UUID;

public interface SendFriendRequestUseCase {

    Friendship send(SendCommand command);

    record SendCommand(UUID senderId, UUID receiverId) {}
}
