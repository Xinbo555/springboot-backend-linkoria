package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.adapter.out;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out.ConversationUserDataPort;
import com.xinbo.springboot.backend.linkoria.app.user.application.service.UserService;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConversationUserDataAdapter implements ConversationUserDataPort {

    private final UserService userService;

    public ConversationUserDataAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ConversationUserData getUserData(UUID userId) {
        User user = userService.getUserById(userId);
        return new ConversationUserData(user.getId(), user.getUsername().getValue(), user.getAvatarUrl());
    }
}
