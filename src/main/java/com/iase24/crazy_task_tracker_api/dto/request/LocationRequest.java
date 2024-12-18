package com.iase24.crazy_task_tracker_api.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationRequest {

    private double latitude;
    private double longitude;
}
