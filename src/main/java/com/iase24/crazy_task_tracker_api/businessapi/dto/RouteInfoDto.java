package com.iase24.crazy_task_tracker_api.businessapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfoDto {
    private double distance; // в метрах
    private double duration; // в секундах
}
