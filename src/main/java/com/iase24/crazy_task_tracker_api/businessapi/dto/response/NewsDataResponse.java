package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Ответ для новостей на разных языках")
public class NewsDataResponse {

    @Schema(description = "Идентификационный номер новостей", example = "1")
    private Long newsId;

    @Schema(description = "Название новости на разных языках", example = """
            Привет мир!
            Hello world!
            """)
    private String newsTitle;

    @Schema(description = "Информация новости на разных языках", example = """
            Привет мир!
            Hello world!
            """)
    private String newsInfo;
}
