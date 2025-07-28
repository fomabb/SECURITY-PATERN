package com.iase24.crazy_task_tracker_api.businessapi.dto;

import lombok.Data;

@Data
public class Path {
    private double distance; // в метрах
    private long time;      // в миллисекундах
}
