package com.iase24.crazy_task_tracker_api.mapper.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.response.DeliveryTermsResponse;
import com.iase24.crazy_task_tracker_api.entity.DeliveryLocation;
import com.iase24.crazy_task_tracker_api.mapper.DeliveryLocationToTermsMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeliveryLocationToTermsMapperImpl implements DeliveryLocationToTermsMapper {
    @Override
    public DeliveryTermsResponse toResponse(DeliveryLocation location) {
        return new DeliveryTermsResponse(location.getCity(), location.getDistrict(), location.getPrice());
    }

    @Override
    public List<DeliveryTermsResponse> toResponse(List<DeliveryLocation> locations) {
        return locations.stream()
                .map(zone -> new DeliveryTermsResponse(zone.getCity(), zone.getDistrict(), zone.getPrice()))
                .toList();
    }
}
