package com.iase24.crazy_task_tracker_api.mapper;

import com.iase24.crazy_task_tracker_api.businessapi.dto.response.DeliveryTermsResponse;
import com.iase24.crazy_task_tracker_api.entity.DeliveryLocation;

import java.util.List;

public interface DeliveryLocationToTermsMapper {

    DeliveryTermsResponse toResponse(DeliveryLocation location);

    List<DeliveryTermsResponse> toResponse(List<DeliveryLocation> locations);
}
