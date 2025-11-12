package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data // Используем @Data, чтобы сервис мог установить roomId
@Schema(description = "Запрос на отправку нового сообщения в чат")
public class MessageSendRequest {

    // roomId будет устанавливаться в контроллере/сервисе, не от клиента
    private UUID roomId;

    @Schema(description = "Текст сообщения", requiredMode = Schema.RequiredMode.REQUIRED, example = "Всем привет!")
    @NotBlank(message = "Сообщение не может быть пустым")
    @Size(max = 4000, message = "Сообщение не должно превышать 4000 символов")
    private String content;
}
