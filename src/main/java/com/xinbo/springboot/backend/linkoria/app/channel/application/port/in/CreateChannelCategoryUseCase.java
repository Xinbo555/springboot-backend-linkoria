package com.xinbo.springboot.backend.linkoria.app.channel.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;

import java.util.UUID;

public interface CreateChannelCategoryUseCase {
    ChannelCategory create(CreateChannelCategoryCommand command);

    record CreateChannelCategoryCommand(UUID requesterId, Long serverId, String name) {}
}
