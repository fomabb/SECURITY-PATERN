package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountLikesResponse {

    private Long newsId;
    private int countLikes;
    private String title;
    private String info;
}
