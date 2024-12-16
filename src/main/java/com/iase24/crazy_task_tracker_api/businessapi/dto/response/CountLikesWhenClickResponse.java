package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountLikesWhenClickResponse {

    private Long newsId;
    private int countLikes;
}
