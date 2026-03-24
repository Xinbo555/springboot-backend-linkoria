package com.xinbo.springboot.backend.linkoria.app.channel.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;

import java.util.UUID;

public interface GetChannelCategoryUseCase {
    ChannelCategory getChannelCategory(GetChannelCategoryQuery query);

    record GetChannelCategoryQuery(UUID requesterId, Long channelCategoryId) {}
}
