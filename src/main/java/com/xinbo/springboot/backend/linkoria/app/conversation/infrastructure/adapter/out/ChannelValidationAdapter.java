package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.adapter.out;

import com.xinbo.springboot.backend.linkoria.app.channel.application.ChannelFacade;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out.ChannelValidationPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChannelValidationAdapter implements ChannelValidationPort {

    private final ChannelFacade channelFacade;

    public ChannelValidationAdapter(ChannelFacade channelFacade) {
        this.channelFacade = channelFacade;
    }

    @Override
    public void validateMember(UUID userId, Long channelId) {
        channelFacade.validateExistsAndMember(userId, channelId);
    }
}