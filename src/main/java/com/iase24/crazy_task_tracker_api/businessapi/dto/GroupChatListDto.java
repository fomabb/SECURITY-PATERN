package com.iase24.crazy_task_tracker_api.businessapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Краткая информация о группе для отображения в списках")
public record GroupChatListDto(
        @Schema(description = "Уникальный идентификатор группы")
        UUID id,

        @Schema(description = "Название группы")
        String title,

        @Schema(description = "URL обложки группы")
        String coverUrl,

        @Schema(description = "Количество участников")
        int memberCount
) {
}
