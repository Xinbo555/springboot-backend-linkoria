package com.xinbo.springboot.backend.linkoria.app.channel.application.port.in;

import java.util.UUID;

public interface DeleteChannelUseCase {
    void delete(DeleteChannelCommand command);

    record DeleteChannelCommand(UUID requesterId, Long channelId) {}
}
