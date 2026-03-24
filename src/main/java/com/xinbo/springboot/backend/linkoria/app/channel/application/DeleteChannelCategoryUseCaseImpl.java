package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.DeleteChannelCategoryUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategoryRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.channel.ChannelCategoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteChannelCategoryUseCaseImpl implements DeleteChannelCategoryUseCase {
    private final ChannelCategoryRepository channelCategoryRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;

    public DeleteChannelCategoryUseCaseImpl(ChannelCategoryRepository channelCategoryRepository, ServerMemberValidationPort serverMemberValidationPort) {
        this.channelCategoryRepository = channelCategoryRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
    }


    @Override
    public void delete(DeleteChannelCategoryCommand command) {
        ChannelCategory channel = channelCategoryRepository.findById(command.channelCategoryId())
                .orElseThrow(() -> new ChannelCategoryNotFoundException("Category not found"));

        serverMemberValidationPort.validateIsAdminOrOwner(command.requesterId(), channel.getServerId());

        channelCategoryRepository.deleteById(command.channelCategoryId());
    }
}
