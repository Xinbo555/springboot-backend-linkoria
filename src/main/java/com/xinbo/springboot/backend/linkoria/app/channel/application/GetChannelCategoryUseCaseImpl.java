package com.xinbo.springboot.backend.linkoria.app.channel.application;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.GetChannelCategoryUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ServerMemberValidationPort;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategoryRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.channel.ChannelCategoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetChannelCategoryUseCaseImpl implements GetChannelCategoryUseCase {
    private final ChannelCategoryRepository channelCategoryRepository;
    private final ServerMemberValidationPort serverMemberValidationPort;

    public GetChannelCategoryUseCaseImpl(ChannelCategoryRepository channelCategoryRepository, ServerMemberValidationPort serverMemberValidationPort) {
        this.channelCategoryRepository = channelCategoryRepository;
        this.serverMemberValidationPort = serverMemberValidationPort;
    }


    @Override
    public ChannelCategory getChannelCategory(GetChannelCategoryQuery query) {
        ChannelCategory channelCategory = channelCategoryRepository.findById(query.channelCategoryId())
                .orElseThrow(() -> new ChannelCategoryNotFoundException("Channel category not found"));

        serverMemberValidationPort.validateIsMember(query.requesterId(), channelCategory.getServerId());
        return channelCategory;
    }
}
