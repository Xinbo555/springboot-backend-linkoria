package com.xinbo.springboot.backend.linkoria.app.channel.application.port.in;

import java.util.UUID;

public interface DeleteChannelCategoryUseCase {
    void delete(DeleteChannelCategoryCommand command);

    record DeleteChannelCategoryCommand(UUID requesterId, Long channelCategoryId) {}
}
