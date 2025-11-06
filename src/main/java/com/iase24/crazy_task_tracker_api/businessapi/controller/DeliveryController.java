package com.iase24.crazy_task_tracker_api.businessapi.controller;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateZonesRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.LocationPointRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.DeliveryTermsResponse;
import com.iase24.crazy_task_tracker_api.businessapi.service.GeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.DELIVERIES_API;

@RestController
@RequestMapping(DELIVERIES_API)
@RequiredArgsConstructor
@Tag(name = "Зоны доставки")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Зоны добавлены / Адрес в зоне доставки"),
        @ApiResponse(responseCode = "406", description = "Адрес вне зоны доставки")
})
public class DeliveryController {

    public static final String SAVE_DELIVERY_LOCATION_URL = "/location";
    public static final String DELIVERY_LOCATION_TERM_URL = "/location-term";
    public static final String DELIVERY_LOCATION_CHECK_URL = "/location-check";

    private final GeoService geoService;

    @Operation(summary = "Добавить зоны доставки в формате GeoJson")
    @PostMapping(SAVE_DELIVERY_LOCATION_URL)
    @ResponseStatus(HttpStatus.OK)
    public void saveAllDeliveryZones(@RequestBody CreateZonesRequest geoJson) {
        geoService.saveAllDeliveryZones(geoJson);
    }

    @Operation(summary = "Получить условия доставки в эту зону")
    @PostMapping(DELIVERY_LOCATION_TERM_URL)
    @ResponseStatus(HttpStatus.OK)
    public DeliveryTermsResponse getDeliveryTerms(@RequestBody LocationPointRequest request) {
        return geoService.getDeliveryTerms(request);
    }

    @Operation(summary = "Находится ли в зоне доставки")
    @PostMapping(DELIVERY_LOCATION_CHECK_URL)
    @ResponseStatus(HttpStatus.OK)
    public void inDeliveryZone(@RequestBody LocationPointRequest request) {
        geoService.inDeliveryZone(request);
    }
}
