package com.xinbo.springboot.backend.linkoria.app.channel.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;

import java.util.List;
import java.util.UUID;

public interface GetChannelCategoriesByServerIdUseCase {
    List<ChannelCategory> getChannelCategories(GetChannelCategoriesByServerIdQuery query);

    record GetChannelCategoriesByServerIdQuery(UUID requesterId, Long serverId) {}
}
