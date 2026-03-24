package com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaChannelRepository extends JpaRepository<ChannelEntity, Long> {
    List<ChannelEntity> findByServerId(Long serverId);
    List<ChannelEntity> findByCategoryId(Long channelCategoryId);
}
