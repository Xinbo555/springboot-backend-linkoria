package com.xinbo.springboot.backend.linkoria.app.channel.application.port.out;

public interface ConversationCreationPort {
    void createForChannel(Long channelId);
}
