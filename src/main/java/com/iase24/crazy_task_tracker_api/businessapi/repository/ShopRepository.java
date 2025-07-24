package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.businessapi.projection.ShopProjection;
import com.iase24.crazy_task_tracker_api.entity.Shop;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query(value = """
            select s.id,
                   s.name,
                   s.address,
                   s.working_hours,
                   round(cast(st_distancesphere(position, :point) as numeric), 2) as distance, s.lat, s.lon from shop s
                   order by distance
            """, nativeQuery = true)
    List<ShopProjection> findAllShopsOrderedByDistance(@Param("point") Point point);

    @Query(value = """
            select s.id,
                   s.name,
                   s.address,
                   s.working_hours,
                   round(cast(st_distancesphere(position, :point) as numeric), 2) as distance, s.lat, s.lon from shop s
                   where id=:id
                   order by distance
            """, nativeQuery = true)
    ShopProjection findShopByIdOrderByDistance(@Param("id") Long id, @Param("point") Point point);

    @Query(value = """
            SELECT
                s.id as id,
                s.name as name,
                s.address as address,
                s.working_hours as workingHours,
                ST_Y(s.position::geometry) as lat,
                ST_X(s.position::geometry) as lon,
                round(cast(st_distancesphere(position, :point) as numeric), 2) as distance, s.lat, s.lon
            FROM shop s
            WHERE ST_DWithin(
                s.position,
                :point,
                :maxDistance
            )
            ORDER BY distance
            LIMIT 1
            """, nativeQuery = true)
    List<ShopProjection> findClosestShopWithinDistance(
            @Param("point") Point point,
            @Param("maxDistance") double maxDistanceMeters
    );

    @Query(value = """
            SELECT
                s.id as id,
                s.name as name,
                s.address as address,
                s.working_hours as workingHours,
                ST_Y(s.position::geometry) as lat,
                ST_X(s.position::geometry) as lon,
                round(cast(st_distancesphere(position, :point) as numeric), 2) as distance, s.lat, s.lon
            FROM shop s
            WHERE id=:id
            ORDER BY distance
            """, nativeQuery = true)
    ShopProjection findShopWithinDistanceById(Long id, Point point);
}
