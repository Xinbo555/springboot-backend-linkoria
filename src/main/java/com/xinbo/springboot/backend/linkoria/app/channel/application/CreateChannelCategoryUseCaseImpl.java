package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.CreateChannelCategoryUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateChannelCategoryUseCaseImpl implements CreateChannelCategoryUseCase {
    private final ChannelCategoryRepository channelCategoryRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;

    public CreateChannelCategoryUseCaseImpl(ChannelCategoryRepository channelCategoryRepository, ServerMemberValidationPort serverMemberValidationPort) {
        this.channelCategoryRepository = channelCategoryRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
    }

    @Override
    public ChannelCategory create(CreateChannelCategoryCommand command) {
        serverMemberValidationPort.validateServerExists(command.serverId());
        serverMemberValidationPort.validateIsAdminOrOwner(command.requesterId(), command.serverId());

        ChannelCategory channelCategory = ChannelCategory.create(command.serverId(), command.name());

        return channelCategoryRepository.save(channelCategory);
    }
}
