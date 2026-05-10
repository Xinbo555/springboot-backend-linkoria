package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out;

import java.util.UUID;

public interface ConversationUserDataPort {
    ConversationUserData getUserData(UUID userId);
    record ConversationUserData(UUID id, String username, String avatarUrl) {}
}
