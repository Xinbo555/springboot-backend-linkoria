package com.xinbo.springboot.backend.linkoria.app.channel.rest.dto.response;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;

import java.time.Instant;

public record ChannelCategoryResponse(Long id, Long ServerId, String name, Instant createdAt) {
    public static ChannelCategoryResponse from(ChannelCategory category) {
        return new ChannelCategoryResponse(
                category.getId(),
                category.getServerId(),
                category.getName(),
                category.getCreatedAt()
        );
    }
}
