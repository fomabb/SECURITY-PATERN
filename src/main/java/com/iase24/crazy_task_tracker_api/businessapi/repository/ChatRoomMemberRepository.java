package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, UUID> {

    /**
     * Проверяет, является ли пользователь участником указанной чат-комнаты.
     * Необходимо для проверки прав доступа перед отправкой/чтением сообщений.
     *
     * @param roomId ID чат-комнаты.
     * @param userId ID пользователя.
     * @return true, если пользователь является участником, иначе false.
     */
    boolean existsByRoomIdAndUserId(UUID roomId, UUID userId);
}
