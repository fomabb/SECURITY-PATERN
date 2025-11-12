package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    /**
     * Находит приватную чат-комнату (PRIVATE) между двумя пользователями.
     * Этот запрос ищет комнату, в которой состоят оба указанных пользователя.
     *
     * @param user1Id ID первого пользователя.
     * @param user2Id ID второго пользователя.
     * @return Optional с чат-комнатой, если она существует.
     */
    @Query("""
        SELECT cr FROM ChatRoom cr
        WHERE cr.type = 'PRIVATE'
        AND EXISTS (SELECT 1 FROM ChatRoomMember crm WHERE crm.room = cr AND crm.userId = :user1Id)
        AND EXISTS (SELECT 1 FROM ChatRoomMember crm WHERE crm.room = cr AND crm.userId = :user2Id)
    """)
    Optional<ChatRoom> findPrivateRoomBetweenUsers(@Param("user1Id") UUID user1Id, @Param("user2Id") UUID user2Id);
}
