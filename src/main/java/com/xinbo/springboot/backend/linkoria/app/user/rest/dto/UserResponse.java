package com.xinbo.springboot.backend.linkoria.app.user.rest.dto;

import com.xinbo.springboot.backend.linkoria.app.user.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

//esta clase tiene el proposito de transferir un Usuario pero sin su password al frontend
public record UserResponse(
        UUID id,
        String username,
        String email,
        String avatarUrl,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername().getValue(),
                user.getEmail().getValue(),
                user.getAvatarUrl(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
