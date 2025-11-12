package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import com.iase24.crazy_task_tracker_api.entity.enumerate.GroupChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Запрос на создание новой группы чата")
public record GroupChatCreateRequest(
        @Schema(description = "Название группы", requiredMode = Schema.RequiredMode.REQUIRED, example = "Любители собак")
        @NotBlank(message = "Название группы не может быть пустым")
        @Size(min = 3, max = 100, message = "Длина названия должна быть от 3 до 100 символов")
        String title,

        @Schema(description = "Описание группы", example = "Обсуждаем все о наших питомцах")
        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        String description,

        @Schema(description = "URL обложки группы", example = "https://example.com/cover.jpg")
        String coverUrl,

        @Schema(description = "Тип группы (доступ)", requiredMode = Schema.RequiredMode.REQUIRED, example = "OPEN")
        @NotNull(message = "Тип группы должен быть указан")
        GroupChatType type,

        @Schema(description = "Является ли это группой клуба", example = "false")
        boolean isClubGroup,

        @Schema(description = "ID клуба (если это группа клуба)", example = "a1b2c3d4-e5f6-...")
        UUID clubId
) {}
