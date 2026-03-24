package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.GetChannelUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.channel.ChannelNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetChannelUseCaseImpl implements GetChannelUseCase {
    private final ChannelRepository channelRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;

    public GetChannelUseCaseImpl(ChannelRepository channelRepository, ServerMemberValidationPort serverMemberValidationPort) {
        this.channelRepository = channelRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
    }

    @Override
    public Channel getChannel(GetChannelQuery query) {
        Channel channel = channelRepository.findById(query.channelId())
                .orElseThrow(() -> new ChannelNotFoundException("Channel not found"));

        serverMemberValidationPort.validateIsMember(query.requesterId(), channel.getServerId());

        return channel;
    }
}
