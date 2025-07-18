package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iase24.crazy_task_tracker_api.businessapi.dto.GeoJsonFeatureDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "Создание зоны доставки, формат GeoJson")
public class CreateZonesRequest {

    @JsonProperty("type")
    @Schema(description = "Тип, по умолчанию FeatureCollection", example = "FeatureCollection")
    private String type;

    @JsonProperty("features")
    @Schema(description = "Список зон")
    List<GeoJsonFeatureDto> features;
}
