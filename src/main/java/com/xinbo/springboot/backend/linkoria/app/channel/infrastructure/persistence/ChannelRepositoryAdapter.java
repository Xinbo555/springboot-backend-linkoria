package com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelRepository;
import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.entity.ChannelEntity;
import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.mapper.ChannelMapper;
import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.repository.JpaChannelRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChannelRepositoryAdapter implements ChannelRepository {

    private final JpaChannelRepository jpaChannelRepository;

    public ChannelRepositoryAdapter(JpaChannelRepository jpaChannelRepository) {
        this.jpaChannelRepository = jpaChannelRepository;
    }

    @Override
    public Optional<Channel> findById(Long id) {
        return jpaChannelRepository.findById(id).map(ChannelMapper::toDomain);
    }

    @Override
    public Channel save(Channel channel) {
        ChannelEntity saved = jpaChannelRepository.save(ChannelMapper.toEntity(channel));
        return ChannelMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaChannelRepository.deleteById(id);
    }

    @Override
    public List<Channel> findByServerId(Long serverId) {
        return jpaChannelRepository.findByServerId(serverId).stream().map(ChannelMapper::toDomain).toList();
    }

    @Override
    public List<Channel> findByCategoryId(Long channelCategoryId) {
        return jpaChannelRepository.findByCategoryId(channelCategoryId).stream().map(ChannelMapper::toDomain).toList();
    }
}
