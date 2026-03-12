package com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;
import com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.persistence.entity.RefreshTokenEntity;

import java.time.Instant;

public class RefreshTokenMapper {
    private RefreshTokenMapper(){}

    public static RefreshToken toDomain(RefreshTokenEntity entity) {
        return RefreshToken.reconstitute(
                entity.getId(),
                entity.getToken(),
                entity.getUserId(),
                entity.getExpiresAt(),
                entity.isRevoked()
        );
    }

    public static RefreshTokenEntity toEntity(RefreshToken domain) {
        return new RefreshTokenEntity(
                domain.getId(),
                domain.getToken(),
                domain.getUserId(),
                domain.getExpiresAt(),
                domain.isRevoked(),
                Instant.now()
        );
    }
}
