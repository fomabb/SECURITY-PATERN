package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateZonesRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.LocationPointRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.DeliveryTermsResponse;

public interface GeoService {

    void saveAllDeliveryZones(CreateZonesRequest geoJson);

    DeliveryTermsResponse getDeliveryTerms(LocationPointRequest request);

    void inDeliveryZone(LocationPointRequest request);
}
