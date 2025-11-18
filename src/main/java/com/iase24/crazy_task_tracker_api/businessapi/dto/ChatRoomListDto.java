package com.iase24.crazy_task_tracker_api.businessapi.dto;

import com.iase24.crazy_task_tracker_api.entity.enumerate.ChatRoomType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Представление чат-комнаты в списке чатов пользователя")
public record ChatRoomListDto(
        @Schema(description = "ID чат-комнаты")
        UUID roomId,

        @Schema(description = "Тип комнаты (GROUP или PRIVATE)")
        ChatRoomType type,

        @Schema(description = "Название чата (название группы или имя собеседника)")
        String title,

        @Schema(description = "URL аватара чата (обложка группы или аватар собеседника)")
        String avatarUrl,

        LastMessageDto lastMessage,

        @Schema(description = "Количество непрочитанных сообщений")
        int unreadMessagesCount
) {
    @Schema(description = "Информация о последнем сообщении в чате")
    public record LastMessageDto(
            @Schema(description = "Текст последнего сообщения")
            String content,

            @Schema(description = "Время отправки последнего сообщения")
            Instant createdAt
    ) {}
}
