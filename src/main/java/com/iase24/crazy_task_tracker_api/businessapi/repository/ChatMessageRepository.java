package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    /**
     * Находит страницу с сообщениями для указанной комнаты, отсортированными по времени создания (сначала новые).
     * Идеально подходит для реализации "бесконечной прокрутки" истории чата.
     *
     * @param roomId   ID чат-комнаты.
     * @param pageable Параметры пагинации (обычно включает сортировку по createdAt DESC).
     * @return Страница с сообщениями.
     */
    Page<ChatMessage> findByRoomId(UUID roomId, Pageable pageable);
}
