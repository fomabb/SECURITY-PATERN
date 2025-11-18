package com.iase24.crazy_task_tracker_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO (Data Transfer Object) с краткой публичной информацией о пользователе.
 * Используется для обогащения других DTO, например, для отображения
 * имени и аватара отправителя в сообщениях чата.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Краткая публичная информация о пользователе")
public class UserDetailsDto {

    @Schema(description = "Уникальный идентификатор пользователя",
            example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Полное имя или никнейм пользователя",
            example = "Иван Иванов")
    private String fullName;

    @Schema(description = "URL аватара пользователя",
            example = "https://example.com/avatars/ivan.jpg")
    private String avatarUrl;
}
