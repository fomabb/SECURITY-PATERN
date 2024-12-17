package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class LikeByUserIdAndNewsIdRequest {

    private Long newsId;
    private UUID userId;
}
