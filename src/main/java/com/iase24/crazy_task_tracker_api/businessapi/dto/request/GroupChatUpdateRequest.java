package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import com.iase24.crazy_task_tracker_api.entity.enumerate.GroupChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на обновление существующей группы чата")
public record GroupChatUpdateRequest(
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
        GroupChatType type
) {}
