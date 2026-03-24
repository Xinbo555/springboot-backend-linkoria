package com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.entity.ChannelEntity;

public class ChannelMapper {
    public static Channel toDomain(ChannelEntity entity) {
        return Channel.reconstitute(
                entity.getId(),
                entity.getServerId(),
                entity.getServerId(),
                entity.getName(),
                entity.getCreatedAt()
        );
    }

    public static ChannelEntity toEntity(Channel channel) {
        return new ChannelEntity(
                channel.getId(),
                channel.getServerId(),
                channel.getCategoryId(),
                channel.getName(),
                channel.getCreatedAt()
        );
    }
}
