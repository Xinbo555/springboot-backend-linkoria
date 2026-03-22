package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.DeleteChannelUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.channel.ChannelNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteChannelUseCaseImpl implements DeleteChannelUseCase {
    private final ChannelRepository channelRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;

    public DeleteChannelUseCaseImpl(ChannelRepository channelRepository, ServerMemberValidationPort serverMemberValidationPort) {
        this.channelRepository = channelRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
    }

    @Override
    public void delete(DeleteChannelCommand command) {
        Channel channel = channelRepository.findById(command.channelId())
                .orElseThrow(() -> new ChannelNotFoundException("Channel not found"));

        serverMemberValidationPort.validateIsAdminOrOwner(command.requesterId(), channel.getServerId());

        channelRepository.deleteById(command.channelId());
    }
}
