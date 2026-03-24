package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.GetChannelsByCategoryIdUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategoryRepository;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.channel.ChannelCategoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetChannelsByCategoryIdUseCaseImpl implements GetChannelsByCategoryIdUseCase {
    private final ChannelRepository channelRepository;
    private final ChannelCategoryRepository channelCategoryRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;

    public GetChannelsByCategoryIdUseCaseImpl(ChannelRepository channelRepository, ChannelCategoryRepository channelCategoryRepository, ServerMemberValidationPort serverMemberValidationPort) {
        this.channelRepository = channelRepository;
        this.channelCategoryRepository = channelCategoryRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
    }

    @Override
    public List<Channel> getChannels(GetChannelsByCategoryIdQuery query) {
        ChannelCategory channelCategory = channelCategoryRepository.findById(query.channelCategoryId())
                .orElseThrow(() -> new ChannelCategoryNotFoundException("Category not found"));

        serverMemberValidationPort.validateIsMember(query.requesterId(), channelCategory.getServerId());

        return channelRepository.findByCategoryId(channelCategory.getId());
    }
}
