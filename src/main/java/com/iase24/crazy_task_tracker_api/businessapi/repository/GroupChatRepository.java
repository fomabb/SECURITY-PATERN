package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.entity.GroupChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface GroupChatRepository extends JpaRepository<GroupChat, UUID>, JpaSpecificationExecutor<GroupChat> {

    /**
     * Находит все группы, в которых состоит указанный пользователь.
     * Spring Data JPA автоматически создаст запрос с JOIN на group_chat_members.
     *
     * @param userId   ID пользователя.
     * @param pageable Параметры пагинации.
     * @return Страница с группами пользователя.
     */
    Page<GroupChat> findByMembers_UserId(UUID userId, Pageable pageable);
}
