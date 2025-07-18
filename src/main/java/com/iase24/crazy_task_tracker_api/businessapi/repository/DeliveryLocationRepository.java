package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.entity.DeliveryLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryLocationRepository extends JpaRepository<DeliveryLocation, UUID> {

    @Query("""
            SELECT COUNT(loc) > 0 FROM DeliveryLocation loc
            WHERE ST_Contains(loc.polygon, ST_SetSRID(ST_MakePoint(:x, :y), 4326))
            """)
    boolean existsLocationContainingPoint(@Param("x") double longitudeX, @Param("y") double latitudeY);

    @Query("""
            SELECT loc FROM DeliveryLocation loc
            WHERE ST_Contains(loc.polygon, ST_SetSRID(ST_MakePoint(:x, :y), 4326))
            """)
    Optional<DeliveryLocation> findLocationByCoordinates(@Param("x") double longitudeX, @Param("y") double latitudeY);

}
