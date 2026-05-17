package com.xinbo.springboot.backend.linkoria.app.user.infrastructure.persistence.mapper;

import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Email;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Username;
import com.xinbo.springboot.backend.linkoria.app.user.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername().getValue(),
                user.getEmail().getValue(),
                user.getPasswordHash(),
                user.getAvatarUrl(),
                user.getBio(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toDomain(UserEntity entity) {
        return User.reconstitute(
                entity.getId(),
                Username.of(entity.getUsername()),
                Email.of(entity.getEmail()),
                entity.getPasswordHash(),
                entity.getAvatarUrl(),
                entity.getBio(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
