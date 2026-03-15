package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.util.UUID;

public interface UnblockUserUseCase {

    Friendship unblock(UnblockCommand command);

    record UnblockCommand(UUID requesterId, UUID targetId) {}
}
