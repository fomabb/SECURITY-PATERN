package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.ShopDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ShopAddRequest;
import com.iase24.crazy_task_tracker_api.businessapi.projection.ShopProjection;
import jakarta.validation.Valid;

import java.util.List;

public interface ShopService {

    ShopProjection getClosestShop(double lat, double lon);

    List<ShopProjection> getAllShopsOrderedByDistance(double lat, double lon);

    ShopDto addShop(@Valid ShopAddRequest request);

    ShopProjection getShopDistanceById(Long id, double lat, double lon);
}
