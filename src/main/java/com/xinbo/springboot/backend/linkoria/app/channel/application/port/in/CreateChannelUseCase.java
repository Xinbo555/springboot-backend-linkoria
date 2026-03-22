package com.xinbo.springboot.backend.linkoria.app.channel.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;

import java.util.UUID;

public interface CreateChannelUseCase {
    Channel create(CreateChannelCommand command);

    record CreateChannelCommand(UUID requesterId, Long serverId, Long channelCategoryId, String name) {
    }
}
