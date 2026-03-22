package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.CreateChannelUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateChannelUseCaseImpl implements CreateChannelUseCase {
    private final ChannelRepository channelRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;

    public CreateChannelUseCaseImpl(ChannelRepository channelRepository, ServerMemberValidationPort serverMemberValidationPort) {
        this.channelRepository = channelRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
    }


    @Override
    public Channel create(CreateChannelCommand command) {
        serverMemberValidationPort.validateServerExists(command.serverId());
        serverMemberValidationPort.validateIsAdminOrOwner(command.requesterId(), command.serverId());

        Channel channel = Channel.create(command.serverId(), command.name());

        return channelRepository.save(channel);
    }
}
