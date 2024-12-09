package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на добавление новостей на разных языках")
public class CreateNewsTwoLanguageRequest {

    @Schema(description = "Оглавление новости на русском языке", example = "Привет мир!")
    private String titleRu;

    @Schema(description = "Оглавление новости на английском языке", example = "Hello world!")
    private String titleEn;

    @Schema(description = "Информация", example = "Привет мир!")
    private String infoNewsRu;

    @Schema(description = "Информация", example = "Hello world!")
    private String infoNewsEn;
}
