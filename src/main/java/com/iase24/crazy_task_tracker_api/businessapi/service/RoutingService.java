package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.RouteInfoDto;

public interface RoutingService {

    RouteInfoDto calculateRoute(double startLon, double startLat, double endLon, double endLat, RoutingMode mode);

    RouteInfoDto calculateRouteGraph(double startLon, double startLat, double endLon, double endLat, RoutingMode mode);

    enum RoutingMode {
        DRIVING,
        WALKING,
        BICYCLE  // опционально
    }
}
