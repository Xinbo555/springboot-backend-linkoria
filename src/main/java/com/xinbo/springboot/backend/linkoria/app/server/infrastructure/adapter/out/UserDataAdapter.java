package com.xinbo.springboot.backend.linkoria.app.server.infrastructure.adapter.out;

import com.xinbo.springboot.backend.linkoria.app.server.application.port.out.UserDataPort;
import com.xinbo.springboot.backend.linkoria.app.user.application.service.UserService;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserDataAdapter implements UserDataPort {
    private final UserService userService;

    public UserDataAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserData> getUserDataBatch(List<UUID> userIds) {
        return userService.findByIds(userIds).stream()
                .map(u -> new UserData(u.getId(), u.getUsername().getValue(), u.getAvatarUrl()))
                .toList();
    }

    @Override
    public UserData getUserData(UUID userId) {
        User user = userService.getUserById(userId);
        return new UserData(user.getId(), user.getUsername().getValue(), user.getAvatarUrl());
    }
}
