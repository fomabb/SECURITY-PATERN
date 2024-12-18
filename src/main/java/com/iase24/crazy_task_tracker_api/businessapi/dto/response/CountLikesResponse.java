package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Возврат новости с использованием различных языков и количеством лайков")
public class CountLikesResponse {

    @Schema(description = "Идентификационный номер новости", example = "1")
    private Long newsId;

    @Schema(description = "Тема новости", example = "News is cool")
    private String title;

    @Schema(description = "Контент новости", example = "This news real is cool")
    private String info;

    @Schema(description = "Количество лайков", example = "12")
    private int countLikes;

    @Schema(description = "Количество дизлайков", example = "1")
    private int disLike;
}
