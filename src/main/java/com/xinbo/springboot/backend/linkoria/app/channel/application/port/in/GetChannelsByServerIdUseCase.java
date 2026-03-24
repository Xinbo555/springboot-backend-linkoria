package com.xinbo.springboot.backend.linkoria.app.channel.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;

import java.util.List;
import java.util.UUID;

public interface GetChannelsByServerIdUseCase {
    List<Channel> getChannels(GetChannelsByServerIdQuery query);

    record GetChannelsByServerIdQuery(UUID requesterId, Long serverId) {}
}
