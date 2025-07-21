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
}
