package com.iase24.crazy_task_tracker_api.businessapi.dto;

import com.iase24.crazy_task_tracker_api.businessapi.projection.ShopProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShopRouteInfoDto {
    private ShopProjection shop;
    private double routeDistance; // реальное расстояние в метрах
    private double routeDuration; // время в секундах
}
