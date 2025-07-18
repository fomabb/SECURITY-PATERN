package com.iase24.crazy_task_tracker_api.businessapi.dto;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.elasticsearch.utils.geohash.Geometry;

@Getter
@Builder
@Schema(description = "Зона доставки")
public class GeoJsonFeatureDto {

    @JsonProperty("type")
    @Schema(description = "Тип, по умолчанию Feature", example = "Feature")
    private String type;

    @JsonProperty("properties")
    @Schema(description = "Параметры зоны доставки")
    private GeoJsonPropertiesDto properties;

    @JsonDeserialize(using = GeometryDeserializer.class)
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonProperty("geometry")
    @Schema(description = "Координаты, объект GEOMETRY(Polygon, 4326)")
    private Geometry geometry;
}
