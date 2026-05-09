package com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.adapter.out;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.out.FriendshipUserDataPort;
import com.xinbo.springboot.backend.linkoria.app.user.application.service.UserService;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FriendshipUserDataAdapter implements FriendshipUserDataPort {

    private final UserService userService;

    public FriendshipUserDataAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public FriendUserData getUserData(UUID userId) {
        User user = userService.getUserById(userId);
        return new FriendUserData(user.getId(), user.getUsername().getValue(), user.getAvatarUrl());
    }
}
