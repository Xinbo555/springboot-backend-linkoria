package com.xinbo.springboot.backend.linkoria.app.channel.domain;

import java.util.List;
import java.util.Optional;

public interface ChannelCategoryRepository {
    Optional<ChannelCategory> findById(Long id);

    ChannelCategory save(ChannelCategory channelCategory);

    List<ChannelCategory> findByServerId(Long serverId);

    void deleteById(Long id);
}
