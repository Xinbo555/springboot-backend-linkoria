package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.CreateChannelUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ConversationCreationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategoryRepository;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelRepository;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.ConversationFacade;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.channel.ChannelCategoryNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.channel.ChannelNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateChannelUseCaseImpl implements CreateChannelUseCase {
    private final ChannelRepository channelRepository;
    private final ChannelCategoryRepository channelCategoryRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;
    private final ConversationCreationPort conversationCreationPort;

    public CreateChannelUseCaseImpl(ChannelRepository channelRepository, ChannelCategoryRepository channelCategoryRepository, ServerMemberValidationPort serverMemberValidationPort, ConversationCreationPort conversationCreationPort) {
        this.channelRepository = channelRepository;
        this.channelCategoryRepository = channelCategoryRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
        this.conversationCreationPort = conversationCreationPort;
    }


    @Override
    public Channel create(CreateChannelCommand command) {
        serverMemberValidationPort.validateServerExists(command.serverId());

        serverMemberValidationPort.validateIsAdminOrOwner(command.requesterId(), command.serverId());

        Channel channel;
        if(command.channelCategoryId() != null) {
            channelCategoryRepository.findById(command.channelCategoryId())
                    .orElseThrow(() -> new ChannelCategoryNotFoundException("Channel not found"));
            channel = Channel.createWithCategory(command.serverId(), command.channelCategoryId(), command.name());
        }else {
            channel = Channel.create(command.serverId(), command.name());
        }

        Channel saved = channelRepository.save(channel);

        conversationCreationPort.createForChannel(saved.getId());

        return saved;
    }
}
