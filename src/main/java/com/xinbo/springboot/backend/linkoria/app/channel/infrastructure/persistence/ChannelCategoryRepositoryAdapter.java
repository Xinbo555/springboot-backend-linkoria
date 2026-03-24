package com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence;

import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategoryRepository;
import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.entity.ChannelCategoryEntity;
import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.mapper.ChannelCategoryMapper;
import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.repository.JpaChannelCategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChannelCategoryRepositoryAdapter implements ChannelCategoryRepository {

    private final JpaChannelCategoryRepository jpaChannelCategoryRepository;

    public ChannelCategoryRepositoryAdapter(JpaChannelCategoryRepository jpaChannelCategoryRepository) {
        this.jpaChannelCategoryRepository = jpaChannelCategoryRepository;
    }

    @Override
    public Optional<ChannelCategory> findById(Long id) {
        return jpaChannelCategoryRepository.findById(id).map(ChannelCategoryMapper::toDomain);
    }

    @Override
    public ChannelCategory save(ChannelCategory channelCategory) {
        ChannelCategoryEntity saved = jpaChannelCategoryRepository.save(ChannelCategoryMapper.toEntity(channelCategory));
        return ChannelCategoryMapper.toDomain(saved);
    }

    @Override
    public List<ChannelCategory> findByServerId(Long serverId) {
        return jpaChannelCategoryRepository.findByServerId(serverId).stream().map(ChannelCategoryMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaChannelCategoryRepository.deleteById(id);
    }
}
