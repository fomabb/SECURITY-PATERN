package com.iase24.crazy_task_tracker_api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetIpClientResponse {

    @JsonFormat(pattern = "([0-9]{1,3}[\\.]){3}[0-9]{1,3}")
    private String ip;
}
