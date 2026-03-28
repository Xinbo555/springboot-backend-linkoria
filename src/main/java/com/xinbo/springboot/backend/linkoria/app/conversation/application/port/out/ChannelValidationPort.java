package com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out;

import java.util.UUID;

public interface ChannelValidationPort {
    void validateMember(UUID requesterId, Long channelId);
}
