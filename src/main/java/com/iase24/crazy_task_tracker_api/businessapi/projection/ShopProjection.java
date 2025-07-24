package com.iase24.crazy_task_tracker_api.businessapi.projection;

import jakarta.annotation.Nullable;

public interface ShopProjection {

    Long getId();

    @Nullable
    String getName();

    @Nullable
    String getAddress();

    @Nullable
    String getWorkingHours();

    @Nullable
    Double getDistance();

    double getLat(); // Широта магазина

    double getLon(); // Долгота магазина
}
