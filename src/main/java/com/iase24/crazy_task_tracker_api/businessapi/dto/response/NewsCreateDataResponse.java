package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ о создании новостей")
public class NewsCreateDataResponse {

    private Long newsRuId;
    private Long newsEnId;
}
