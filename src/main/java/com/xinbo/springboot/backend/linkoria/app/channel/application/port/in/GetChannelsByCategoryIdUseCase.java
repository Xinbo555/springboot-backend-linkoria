package com.xinbo.springboot.backend.linkoria.app.channel.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;

import java.util.List;
import java.util.UUID;

public interface GetChannelsByCategoryIdUseCase {
    List<Channel> getChannels(GetChannelsByCategoryIdQuery query);

    record GetChannelsByCategoryIdQuery(UUID requesterId, Long channelCategoryId) {}
}
