package com.xinbo.springboot.backend.linkoria.app.channel.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;

import java.util.UUID;

public interface GetChannelUseCase {
    Channel getChannel(GetChannelQuery query);

    record GetChannelQuery(UUID requesterId, Long channelId) {}
}
