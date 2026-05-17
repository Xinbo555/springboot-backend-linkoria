package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.message.infrastructure.persistence.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaMessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query(value = """
        SELECT * FROM messages
                WHERE conversation_id = :conversationId
                AND (:cursor IS NULL OR id < :cursor)
                ORDER BY id DESC
                LIMIT :limit
        """, nativeQuery = true)
    List<MessageEntity> findBackwards(
            @Param("conversationId") Long conversationId,
            @Param("cursor") Long cursor,
            @Param("limit") int limit
    );

    @Query(value = """
        SELECT * FROM messages
                WHERE conversation_id = :conversationId
                AND (:cursor IS NULL OR id > :cursor)
                ORDER BY id ASC
                LIMIT :limit
        """, nativeQuery = true)
    List<MessageEntity> findForwards(
            @Param("conversationId") Long conversationId,
            @Param("cursor") Long cursor,
            @Param("limit") int limit
    );

    Long deleteByConversationId(Long conversationId);

    Long countByConversationId(Long conversationId);

    @Query(value = "SELECT * FROM messages WHERE conversation_id = :conversationId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<MessageEntity> findLastMessageInConversation(@Param("conversationId") Long conversationId);
}
