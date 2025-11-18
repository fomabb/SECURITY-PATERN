package com.iase24.crazy_task_tracker_api.businessapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Представление сообщения в чате")
public class ChatMessageDto {
    @Schema(description = "ID сообщения")
    private UUID id;

    @Schema(description = "ID комнаты, к которой относится сообщение")
    private UUID roomId;

    @Schema(description = "ID отправителя")
    private UUID senderId;

    @Schema(description = "Имя отправителя")
    private String senderName;

    @Schema(description = "URL аватара отправителя")
    private String senderAvatarUrl;

    @Schema(description = "Текст сообщения")
    private String content;

    @Schema(description = "Время отправки")
    private Instant createdAt;
}
