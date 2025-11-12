package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.ChatMessageDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.MessageSendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface ChatService {

    String answerMessageDump(String data);

    /**
     * Отправляет сообщение в указанную чат-комнату.
     * Проверяет, является ли отправитель участником комнаты.
     * Сохраняет сообщение в БД и возвращает DTO для рассылки по WebSocket.
     *
     * @param request DTO с текстом сообщения и ID комнаты.
     * @param senderId ID пользователя, отправляющего сообщение.
     * @return Обогащенное DTO сохраненного сообщения.
     */
    ChatMessageDto sendMessage(MessageSendRequest request, UUID senderId) throws AccessDeniedException;

    /**
     * Получает страницу с историей сообщений для указанной комнаты.
     * Проверяет, имеет ли текущий пользователь доступ к этой комнате.
     *
     * @param roomId ID комнаты.
     * @param currentUserId ID пользователя, запрашивающего историю.
     * @param pageable Параметры пагинации (рекомендуется сортировка по createdAt DESC).
     * @return Страница с DTO сообщений.
     */
    Page<ChatMessageDto> getMessagesForRoom(UUID roomId, UUID currentUserId, Pageable pageable) throws AccessDeniedException;

    /**
     * Находит существующую приватную комнату между двумя пользователями или создает новую.
     *
     * @param userFirstId ID первого пользователя (обычно текущего).
     * @param userSecondId ID второго пользователя.
     * @return ID существующей или новой чат-комнаты.
     */
    UUID findOrCreatePrivateRoom(UUID userFirstId, UUID userSecondId);
}
