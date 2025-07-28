package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.RouteInfoDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.ShopDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.ShopRouteInfoDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ShopAddRequest;
import com.iase24.crazy_task_tracker_api.businessapi.projection.ShopProjection;
import com.iase24.crazy_task_tracker_api.businessapi.repository.ShopRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.RoutingService;
import com.iase24.crazy_task_tracker_api.businessapi.service.ShopService;
import com.iase24.crazy_task_tracker_api.entity.Shop;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.iase24.crazy_task_tracker_api.businessapi.service.RoutingService.RoutingMode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopServiceImpl implements ShopService {

    private static final double DEFAULT_SEARCH_RADIUS = 5000;
    private final GeometryFactory geometryFactory;

    private final ShopRepository shopRepository;
    private final RoutingService routingService;

    @Override
    @Transactional
    public ShopDto addShop(ShopAddRequest request) {
        Shop shop = Shop.builder()
                .name(request.getName())
                .address(request.getAddress())
                .workingHours(request.getWorkingHours())
                .lat(request.getLatitude())
                .lon(request.getLongitude())
                .build();

        Point point = geometryFactory.createPoint(new Coordinate(request.getLongitude(), request.getLatitude()));
        point.setSRID(4326);
        shop.setPosition(point);

        Shop saved = shopRepository.save(shop);

        return ShopDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .address(saved.getAddress())
                .workingHours(saved.getWorkingHours())
                .build();
    }

    @Override
    public ShopProjection getClosestShop(double lat, double lon) {
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        List<ShopProjection> shops = shopRepository.findAllShopsOrderedByDistance(point);

        return shops.isEmpty() ? null : shops.getFirst();
    }

    @Override
    public List<ShopProjection> getAllShopsOrderedByDistance(double lat, double lon) {
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));

        return shopRepository.findAllShopsOrderedByDistance(point);
    }

    @Override
    public ShopProjection getShopDistanceById(Long id, double lat, double lon) {
        Shop shopId = shopRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Shop with %s id not found", id)));
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));

        return shopRepository.findShopByIdOrderByDistance(shopId.getId(), point);
    }

    @Override
    public ShopRouteInfoDto getClosestShopWithRoute(double lat, double lon, RoutingMode mode) {
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        List<ShopProjection> shops = shopRepository.findClosestShopWithinDistance(point, DEFAULT_SEARCH_RADIUS);

        if (shops.isEmpty()) {
            return null;
        }

        ShopProjection closest = shops.getFirst();

        RouteInfoDto route = routingService.calculateRouteGraph(lon, lat, closest.getLon(), closest.getLat(), mode);

        return new ShopRouteInfoDto(closest, route.getDistance(), route.getDuration(), mode.toString());
    }

    @Override
    public ShopRouteInfoDto getShopWithRouteById(Long id, double lat, double lon, RoutingMode mode) {
        Shop shopId = shopRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Shop with %s id not found", id)));
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        ShopProjection shop = shopRepository.findShopWithinDistanceById(shopId.getId(), point);


        RouteInfoDto route = routingService.calculateRouteGraph(lon, lat, shop.getLon(), shop.getLat(), mode);

        return new ShopRouteInfoDto(shop, route.getDistance(), route.getDuration(), mode.name());
    }
}
