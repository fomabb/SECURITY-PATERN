package com.iase24.crazy_task_tracker_api.businessapi.dto;

import com.iase24.crazy_task_tracker_api.entity.enumerate.GroupChatType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Полная информация о группе чата")
public record GroupChatDetailsDto(
        @Schema(description = "Уникальный идентификатор группы")
        UUID id,

        @Schema(description = "ID связанной чат-комнаты (для подписки по WebSocket)")
        UUID chatRoomId,

        @Schema(description = "Название группы")
        String title,

        @Schema(description = "Описание группы")
        String description,

        @Schema(description = "URL обложки группы")
        String coverUrl,

        @Schema(description = "Тип группы")
        GroupChatType type,

        @Schema(description = "ID пользователя-создателя")
        UUID createdByUserId,

        @Schema(description = "Дата создания")
        Instant createdAt
) {
}
