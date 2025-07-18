package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateZonesRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.LocationPointRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.DeliveryTermsResponse;
import com.iase24.crazy_task_tracker_api.businessapi.repository.DeliveryLocationRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.GeoService;
import com.iase24.crazy_task_tracker_api.entity.DeliveryLocation;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.Message;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.RestException;
import com.iase24.crazy_task_tracker_api.mapper.DeliveryLocationToTermsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class DeliveryGeoServiceImpl implements GeoService {

    private final DeliveryLocationRepository locationRepository;
    private final DeliveryLocationToTermsMapper locationToTermsMapper;

    @Override
    public void saveAllDeliveryZones(CreateZonesRequest geoJson) {
        var zones = geoJson.getFeatures().stream()
                .map(f -> DeliveryLocation.builder()
                        .city(f.getProperties().getDistrict())
                        .district(f.getProperties().getDistrict())
                        .price(f.getProperties().getPrice())
                        .fill(f.getProperties().getFill())
                        .polygon((Polygon) f.getGeometry())
                        .build())
                .toList();

        locationRepository.saveAll(zones);
    }

    @Override
    public DeliveryTermsResponse getDeliveryTerms(LocationPointRequest request) {
        var location = locationRepository.findLocationByCoordinates(
                request.getLongitudeX(), request.getLatitudeY()
        ).orElseThrow(() -> new RestException(Message.ADDRESS_OUT_OF_DELIVERY_ZONE));

        return locationToTermsMapper.toResponse(location);
    }

    @Override
    public void inDeliveryZone(LocationPointRequest request) {
        var isDeliverable = locationRepository.existsLocationContainingPoint(
                request.getLongitudeX(), request.getLatitudeY()
        );

        if (!isDeliverable) throw new RestException(Message.ADDRESS_OUT_OF_DELIVERY_ZONE);
    }
}
