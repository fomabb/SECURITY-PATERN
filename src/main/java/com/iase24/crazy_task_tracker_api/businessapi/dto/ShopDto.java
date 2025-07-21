package com.iase24.crazy_task_tracker_api.businessapi.dto;

import lombok.Builder;

@Builder
public record ShopDto(Long id, String name, String address, String workingHours, Double distance) {

}
