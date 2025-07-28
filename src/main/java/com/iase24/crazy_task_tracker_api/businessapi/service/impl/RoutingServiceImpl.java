package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.Path;
import com.iase24.crazy_task_tracker_api.businessapi.dto.RouteInfoDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.GraphHopperResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.OsrmResponse;
import com.iase24.crazy_task_tracker_api.businessapi.service.RoutingService;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.iase24.crazy_task_tracker_api.util.constant.ConstantProject.OSRM_URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoutingServiceImpl implements RoutingService {

    private final RestTemplate restTemplate;
    private static final String GRAPHHOPPER_URL = "https://graphhopper.com/api/1";
    private static final String API_KEY = "17952215-a3a7-45b7-a17b-176c43b750eb";

    @Override
    public RouteInfoDto calculateRoute(double startLon, double startLat, double endLon, double endLat, RoutingMode mode) {

        String profile = switch (mode) {
            case DRIVING -> "car";
            case WALKING -> "foot";
            case BICYCLE -> "bike";
        };

        String url = String.format("%s/%s/%s,%s;%s,%s?overview=false",
                OSRM_URL, profile, startLon, startLat, endLon, endLat);

        ResponseEntity<OsrmResponse> response = restTemplate.getForEntity(url, OsrmResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getRoutes().getFirst();
        }
        throw new BusinessException("Routing calculation failed");
    }

    @Override
    public RouteInfoDto calculateRouteGraph(double startLon, double startLat, double endLon, double endLat, RoutingMode mode) {

        String profile = switch (mode) {
            case DRIVING -> "car";
            case WALKING -> "foot";
            case BICYCLE -> "bike";
        };

        String url = String.format("%s/route?point=%s,%s&point=%s,%s&vehicle=%s&key=%s",
                GRAPHHOPPER_URL, startLat, startLon, endLat, endLon, profile, API_KEY);

        try {
            GraphHopperResponse response = restTemplate.getForObject(url, GraphHopperResponse.class);

            if (response != null && !response.getPaths().isEmpty()) {
                Path path = response.getPaths().getFirst();
                return new RouteInfoDto(
                        path.getDistance(), // метры
                        path.getTime() / 1000.0 // переводим мс в секунды
                );
            }
        } catch (RestClientException e) {
            log.error("GraphHopper API request failed", e);
        }

        throw new BusinessException("Routing calculation failed");
    }
}
