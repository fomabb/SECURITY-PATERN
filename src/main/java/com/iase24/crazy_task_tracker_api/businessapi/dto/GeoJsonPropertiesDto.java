package com.iase24.crazy_task_tracker_api.businessapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@Schema(description = "Параметры зоны доставки")
public class GeoJsonPropertiesDto {

    @JsonProperty("city")
    @Schema(description = "Город", example = "Волгоград")
    private String city;

    @JsonProperty("district")
    @Schema(description = "Район", example = "Центральный район")
    private String district;

    @JsonProperty("price_rub")
    @Schema(description = "Цена в рублях", example = "100")
    private BigDecimal price;

    @JsonProperty("fill")
    @Schema(description = "Цвет зоны", example = "#37ab0d")
    private String fill;
}
