package com.xinbo.springboot.backend.linkoria.app.channel.rest.dto.response;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;

import java.time.Instant;

public record ChannelResponse(Long id, Long ServerId, Long ChannelCategoryId, String name, Instant createdAt) {
    public static ChannelResponse from(Channel channel) {
        return new ChannelResponse(
                channel.getId(),
                channel.getServerId(),
                channel.getCategoryId(),
                channel.getName(),
                channel.getCreatedAt()
        );
    }
}
