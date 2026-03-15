package com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.persistence.entity.FriendshipEntity;

public class FriendshipMapper {

    private FriendshipMapper() {
    }

    public static Friendship toDomain(FriendshipEntity entity) {
        return Friendship.reconstitute(entity.getId(),
                entity.getSenderId(),
                entity.getReceiverId(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getBlockedBy());
    }

    public static FriendshipEntity toEntity(Friendship domain) {
        return new FriendshipEntity(domain.getId(),
                domain.getSenderId(),
                domain.getReceiverId(),
                domain.getStatus(),
                domain.getCreatedAt(),
                domain.getUpdatedAt(),
                domain.getBlockedBy());
    }
}
