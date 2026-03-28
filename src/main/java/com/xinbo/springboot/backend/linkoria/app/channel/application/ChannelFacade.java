package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.GetChannelUseCase;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChannelFacade {

    private final GetChannelUseCase getChannelUseCase;

    public ChannelFacade(GetChannelUseCase getChannelUseCase) {
        this.getChannelUseCase = getChannelUseCase;
    }

    public void validateExistsAndMember(UUID requesterId, Long channelId) {
        getChannelUseCase.getChannel(new GetChannelUseCase.GetChannelQuery(requesterId, channelId));
    }
}
