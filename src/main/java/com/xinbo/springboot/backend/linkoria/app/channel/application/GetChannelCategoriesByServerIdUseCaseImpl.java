package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.GetChannelCategoriesByServerIdUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetChannelCategoriesByServerIdUseCaseImpl implements GetChannelCategoriesByServerIdUseCase {
    private final ChannelCategoryRepository channelCategoryRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;

    public GetChannelCategoriesByServerIdUseCaseImpl(ChannelCategoryRepository channelCategoryRepository, ServerMemberValidationPort serverMemberValidationPort) {
        this.channelCategoryRepository = channelCategoryRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
    }

    @Override
    public List<ChannelCategory> getChannelCategories(GetChannelCategoriesByServerIdQuery query) {
        serverMemberValidationPort.validateServerExists(query.serverId());
        serverMemberValidationPort.validateIsMember(query.requesterId(), query.serverId());

        return channelCategoryRepository.findByServerId(query.serverId());
    }
}
