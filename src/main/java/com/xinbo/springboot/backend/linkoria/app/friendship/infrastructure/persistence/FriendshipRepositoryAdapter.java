package com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.persistence;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipRepository;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.persistence.entity.FriendshipEntity;
import com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.persistence.mapper.FriendshipMapper;
import com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.persistence.repository.JpaFriendshipRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FriendshipRepositoryAdapter implements FriendshipRepository {

    private final JpaFriendshipRepository jpaFriendshipRepository;

    public FriendshipRepositoryAdapter(JpaFriendshipRepository jpaFriendshipRepository) {
        this.jpaFriendshipRepository = jpaFriendshipRepository;
    }

    @Override
    public Friendship save(Friendship friendship) {
        FriendshipEntity entity = FriendshipMapper.toEntity(friendship);
        FriendshipEntity saved = jpaFriendshipRepository.save(entity);
        return FriendshipMapper.toDomain(saved);
    }

    @Override
    public List<Friendship> findByUserIdAndStatus(UUID id, FriendshipStatus status) {
        return jpaFriendshipRepository.findByUserIdAndStatus(id, status)
                .stream().map(FriendshipMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Friendship> findBySenderReceiverId(UUID senderId, UUID receiverId) {
        return jpaFriendshipRepository.findBySenderReceiverId(senderId,receiverId)
                .map(FriendshipMapper::toDomain);
    }

    @Override
    public boolean existsByUsersAndStatusIn(UUID senderId, UUID receiverId, List<FriendshipStatus> statuses) {
        return jpaFriendshipRepository.existsByUsersAndStatusIn(senderId, receiverId, statuses);
    }
}
