package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.entity.GroupChatMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GroupChatMemberRepository extends JpaRepository<GroupChatMember, UUID> {

    /**
     * Находит запись об участнике по ID группы и ID пользователя.
     * Используется для проверки членства или получения роли.
     *
     * @param groupChatId ID группы.
     * @param userId ID пользователя.
     * @return Optional с участником, если он найден.
     */
    Optional<GroupChatMember> findByGroupChatIdAndUserId(UUID groupChatId, UUID userId);

    /**
     * Проверяет, является ли пользователь участником указанной группы.
     * Более эффективен, чем findBy, так как выполняет COUNT-запрос.
     *
     * @param groupChatId ID группы.
     * @param userId ID пользователя.
     * @return true, если пользователь является участником, иначе false.
     */
    boolean existsByGroupChatIdAndUserId(UUID groupChatId, UUID userId);

    /**
     * Возвращает страницу с участниками конкретной группы.
     *
     * @param groupChatId ID группы.
     * @param pageable Параметры пагинации.
     * @return Страница с участниками.
     */
    Page<GroupChatMember> findByGroupChatId(UUID groupChatId, Pageable pageable);
}
