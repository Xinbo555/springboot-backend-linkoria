package com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence;

import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerMember;
import com.xinbo.springboot.backend.linkoria.app.server.domain.ServerRepository;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity.ServerEntity;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.entity.ServerMemberEntity;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.mapper.ServerMapper;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.mapper.ServerMemberMapper;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.repository.JpaServerMemberRepository;
import com.xinbo.springboot.backend.linkoria.app.server.infrastructure.persistence.repository.JpaServerRepository;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.general.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ServerRepositoryAdapter implements ServerRepository {

    private final JpaServerMemberRepository jpaServerMemberRepository;
    private final JpaServerRepository jpaServerRepository;

    public ServerRepositoryAdapter(JpaServerMemberRepository jpaServerMemberRepository, JpaServerRepository jpaServerRepository) {
        this.jpaServerMemberRepository = jpaServerMemberRepository;
        this.jpaServerRepository = jpaServerRepository;
    }

    @Override
    public Optional<Server> findServerById(Long serverId) {
        return jpaServerRepository.findById(serverId)
                .map(ServerMapper::toDomain);
    }

    @Override
    public List<ServerMember> findMembersByServerId(Long serverId) {
        return jpaServerMemberRepository.findByServer_Id(serverId)
                .stream()
                .map(ServerMemberMapper::toDomain)
                .toList();
    }

    @Override
    public Server save(Server server) {
        ServerEntity entity = ServerMapper.toEntity(server);
        ServerEntity saved = jpaServerRepository.save(entity);
        return ServerMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long serverId) {
        jpaServerRepository.deleteById(serverId);
    }

    @Override
    public Optional<Server> findByInviteCode(String inviteCode) {
        return  jpaServerRepository.findByInviteCode(inviteCode)
                .map(ServerMapper::toDomain);
    }

    @Override
    public ServerMember saveMember(ServerMember member) {
        ServerEntity serverEntity = jpaServerRepository.findById(member.getServerId())
                .orElseThrow(() -> new ResourceNotFoundException("No server found"));
        ServerMemberEntity entity = ServerMemberMapper.toEntity(member, serverEntity);
        ServerMemberEntity saved = jpaServerMemberRepository.save(entity);
        return ServerMemberMapper.toDomain(saved);
    }

    @Override
    @Transactional
    public void deleteMember(Long serverId, UUID userId) {
        jpaServerMemberRepository.deleteByServer_IdAndUserId(serverId, userId);
    }

    @Override
    public Optional<ServerMember> findMember(Long serverId, UUID userId) {
        return jpaServerMemberRepository.findByServer_IdAndUserId(serverId, userId)
                .map(ServerMemberMapper::toDomain);
    }

    @Override
    public List<Server> findServersByUserId(UUID userId) {
        return jpaServerRepository.findAllByUserParticipation(userId).stream()
                .map(ServerMapper::toDomain).toList();
    }
}
