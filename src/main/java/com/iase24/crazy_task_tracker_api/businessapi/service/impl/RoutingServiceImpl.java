package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.RouteInfoDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.OsrmResponse;
import com.iase24.crazy_task_tracker_api.businessapi.service.RoutingService;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.iase24.crazy_task_tracker_api.util.constant.ConstantProject.OSRM_URL;

@Service
@RequiredArgsConstructor
public class RoutingServiceImpl implements RoutingService {

    private final RestTemplate restTemplate;


    @Override
    public RouteInfoDto calculateRoute(double startLon, double startLat, double endLon, double endLat) {

        String url = String.format("%s%s,%s;%s,%s?overview=false",
                OSRM_URL,
                startLon, startLat,
                endLon, endLat);

        ResponseEntity<OsrmResponse> response = restTemplate.getForEntity(url, OsrmResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getRoutes().getFirst();
        }
        throw new BusinessException("Routing calculation failed");
    }
}
