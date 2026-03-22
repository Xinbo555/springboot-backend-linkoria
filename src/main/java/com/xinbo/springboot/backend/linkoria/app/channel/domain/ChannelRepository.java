package com.xinbo.springboot.backend.linkoria.app.channel.domain;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository {
    Optional<Channel> findById(Long id);

    Channel save(Channel channel);

    void deleteById(Long id);

    List<Channel> findByServerId(Long serverId);

    List<Channel> findByCategoryId(Long channelCategoryId);
}
