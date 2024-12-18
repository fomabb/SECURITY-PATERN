package com.iase24.crazy_task_tracker_api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeoLocationClient {

    private String city;
    private String region;
    private String country;
    private String zip;
    private double lat;
    private double lon;
    private String ip;
}
