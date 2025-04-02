package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@Schema(description = "Запрос на добавления лайка под новостью")
public class LikeByUserIdAndNewsIdRequest {

    @Schema(description = "Идентификационный номер новости", example = "1")
    private Long newsId;

    @Schema(description = "Идентификационный номер пользователя", example = "53ac777f-fa99-4e47-875d-00782a0b8049")
    private UUID userId;
}
