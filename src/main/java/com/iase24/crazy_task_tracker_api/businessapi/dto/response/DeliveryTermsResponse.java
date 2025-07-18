package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Условия доставки")
public class DeliveryTermsResponse {

    @JsonProperty("city")
    @Schema(description = "Город")
    private String city;

    @JsonProperty("district")
    @Schema(description = "Район")
    private String district;

    @JsonProperty("price_rub")
    @Schema(description = "Цена в рублях")
    private BigDecimal price;
}
