package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;

import java.util.UUID;

public interface BlockUserUseCase {

    Friendship block(BlockCommand command);

    record BlockCommand(UUID requesterId, UUID targetId) {}
}
