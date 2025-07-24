package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import com.iase24.crazy_task_tracker_api.businessapi.dto.RouteInfoDto;
import lombok.Data;

import java.util.List;

@Data
public class OsrmResponse {
    private List<RouteInfoDto> routes;
}
