package com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;
import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.entity.ChannelCategoryEntity;

public class ChannelCategoryMapper {
    public static ChannelCategory toDomain(ChannelCategoryEntity entity) {
        return ChannelCategory.reconstitute(
                entity.getId(),
                entity.getserverId(),
                entity.getName(),
                entity.getCreated_at()
        );
    }

    public static ChannelCategoryEntity toEntity(ChannelCategory domain) {
        return new ChannelCategoryEntity(
                domain.getId(),
                domain.getServerId(),
                domain.getName(),
                domain.getCreatedAt()
        );
    }
}
