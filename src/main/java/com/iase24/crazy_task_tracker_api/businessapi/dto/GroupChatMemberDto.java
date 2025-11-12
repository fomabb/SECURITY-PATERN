package com.iase24.crazy_task_tracker_api.businessapi.dto;

import com.iase24.crazy_task_tracker_api.entity.enumerate.GroupChatRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Информация об участнике группы")
public record GroupChatMemberDto(
        @Schema(description = "ID пользователя")
        UUID userId,

        // Примечание: Эти поля нужно будет обогащать данными из вашего UserService
        @Schema(description = "Имя пользователя")
        String userName,

        @Schema(description = "URL аватара пользователя")
        String userAvatarUrl,

        @Schema(description = "Роль пользователя в группе")
        GroupChatRole role,

        @Schema(description = "Дата вступления в группу")
        Instant joinedAt
) {}
