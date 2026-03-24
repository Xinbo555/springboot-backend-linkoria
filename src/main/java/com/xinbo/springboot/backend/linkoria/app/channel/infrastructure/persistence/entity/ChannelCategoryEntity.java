package com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "channel_category")
public class ChannelCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server_id", nullable = false)
    private Long serverId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant created_at;

    protected ChannelCategoryEntity() {
    }

    public ChannelCategoryEntity(Long id, Long serverId, String name, Instant created_at) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.created_at = created_at;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getserverId() {
        return serverId;
    }
}
