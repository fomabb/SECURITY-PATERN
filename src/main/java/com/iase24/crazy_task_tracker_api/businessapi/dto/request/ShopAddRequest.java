package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopAddRequest {

    @NotBlank
    @Schema(description = "`Название объекта офиса`", example = "Санта")
    private String name;

    @Schema(description = "`Адрес объекта`", example = "г.Жабинка, ул. Центральная, 28")
    private String address;

    @Schema(description = "`Время работы`", example = "c 8:00 до 22:00 Без выходных")
    private String workingHours;

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    @Schema(description = "`Широта по оси Y`", example = "52.19445")
    private Double latitude;

    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    @Schema(description = "`долгота по оси X`", example = "24.03341")
    private Double longitude;
}
