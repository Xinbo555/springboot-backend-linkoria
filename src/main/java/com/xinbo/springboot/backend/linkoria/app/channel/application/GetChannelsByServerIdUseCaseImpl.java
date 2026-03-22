package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.GetChannelsByServerIdUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetChannelsByServerIdUseCaseImpl implements GetChannelsByServerIdUseCase {
    private final ChannelRepository channelRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;

    public GetChannelsByServerIdUseCaseImpl(ChannelRepository channelRepository, ServerMemberValidationPort serverMemberValidationPort) {
        this.channelRepository = channelRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
    }

    @Override
    public List<Channel> getChannels(GetChannelsByServerIdQuery query) {
        serverMemberValidationPort.validateServerExists(query.serverId());

        serverMemberValidationPort.validateIsMember(query.requesterId(), query.serverId());

        return channelRepository.findByServerId(query.serverId());
    }
}
