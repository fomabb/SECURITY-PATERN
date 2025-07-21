package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.ShopDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ShopAddRequest;
import com.iase24.crazy_task_tracker_api.businessapi.projection.ShopProjection;
import com.iase24.crazy_task_tracker_api.businessapi.repository.ShopRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.ShopService;
import com.iase24.crazy_task_tracker_api.entity.Shop;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopServiceImpl implements ShopService {

    private static final int SRID = 4326;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);

    private final ShopRepository shopRepository;

    @Override
    public ShopProjection getClosestShop(double lat, double lon) {
        Point point = geometryFactory.createPoint(new Coordinate(lat, lon));
        List<ShopProjection> shops = shopRepository.findAllShopsOrderedByDistance(point);

        return shops.isEmpty() ? null : shops.getFirst();
    }

    @Override
    public List<ShopProjection> getAllShopsOrderedByDistance(double lat, double lon) {
        Point point = geometryFactory.createPoint(new Coordinate(lat, lon));

        return shopRepository.findAllShopsOrderedByDistance(point);
    }

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

        Point point = geometryFactory.createPoint(new Coordinate(request.getLatitude(), request.getLongitude()));
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
}
