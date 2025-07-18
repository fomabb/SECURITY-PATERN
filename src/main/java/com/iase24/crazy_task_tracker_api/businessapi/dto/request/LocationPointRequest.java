package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Координаты")
public class LocationPointRequest {

    @JsonProperty("latitudeY")
    @Schema(description = "Широта", example = "48.716496")
    private Float latitudeY;

    @JsonProperty("longitudeX")
    @Schema(description = "Долгота", example = "44.530353")
    private Float longitudeX;

}
