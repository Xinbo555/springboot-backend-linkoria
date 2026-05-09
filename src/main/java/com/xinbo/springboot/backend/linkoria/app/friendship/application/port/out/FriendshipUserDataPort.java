package com.xinbo.springboot.backend.linkoria.app.friendship.application.port.out;


import java.util.UUID;

public interface FriendshipUserDataPort {
    FriendUserData getUserData(UUID userId);
    record FriendUserData(UUID id, String username, String avatarUrl) {}
}
