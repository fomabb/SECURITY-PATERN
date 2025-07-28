package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.ShopDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.ShopRouteInfoDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ShopAddRequest;
import com.iase24.crazy_task_tracker_api.businessapi.projection.ShopProjection;
import jakarta.validation.Valid;

import java.util.List;

import static com.iase24.crazy_task_tracker_api.businessapi.service.RoutingService.RoutingMode;

public interface ShopService {

    ShopProjection getClosestShop(double lat, double lon);

    List<ShopProjection> getAllShopsOrderedByDistance(double lat, double lon);

    ShopDto addShop(@Valid ShopAddRequest request);

    ShopProjection getShopDistanceById(Long id, double lat, double lon);

    ShopRouteInfoDto getClosestShopWithRoute(double lat, double lon, RoutingMode mode);

    ShopRouteInfoDto getShopWithRouteById(Long id, double lat, double lon, RoutingMode mode);
}
