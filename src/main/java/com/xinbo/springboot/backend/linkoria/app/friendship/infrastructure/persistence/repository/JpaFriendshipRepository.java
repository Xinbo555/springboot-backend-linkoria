package com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import com.xinbo.springboot.backend.linkoria.app.friendship.infrastructure.persistence.entity.FriendshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaFriendshipRepository extends JpaRepository<FriendshipEntity, Long> {
    @Query("SELECT f FROM FriendshipEntity f " +
            "WHERE (f.senderId = :senderId OR f.receiverId = :senderId) " +
            "AND f.status = :status")
    List<FriendshipEntity> findByUserIdAndStatus(
            @Param("senderId")UUID senderId,
            @Param("status") FriendshipStatus statuses
    );

    @Query("SELECT f FROM FriendshipEntity f " +
            "WHERE f.senderId = :senderId AND f.receiverId = :receiverId")
    Optional<FriendshipEntity> findBySenderReceiverId(
            @Param("senderId")UUID senderId,
            @Param("receiverId") UUID receiverId
    );

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM FriendshipEntity f " +
            "WHERE (f.senderId = :senderId AND f.receiverId = :receiverId " +
            "OR f.senderId = :receiverId AND f.receiverId= :senderId) " +
            "AND f.status IN :statuses")
    boolean existsByUsersAndStatusIn(
            @Param("senderId")UUID senderId,
            @Param("receiverId") UUID receiverId,
            @Param("statuses") List<FriendshipStatus> statuses
    );
}
