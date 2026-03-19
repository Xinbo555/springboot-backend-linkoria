package com.xinbo.springboot.backend.linkoria.app.server.application.port.out;

import java.util.List;
import java.util.UUID;

public interface UserDataPort {
    List<UserData> getUserDataBatch(List<UUID> userIds);

    UserData getUserData(UUID userId);
    record UserData(UUID id, String username, String avatarUrl) {}
}
