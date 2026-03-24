package com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.entity.ChannelCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaChannelCategoryRepository extends JpaRepository<ChannelCategoryEntity,Long> {
    List<ChannelCategoryEntity> findByServerId(Long serverId);
}
