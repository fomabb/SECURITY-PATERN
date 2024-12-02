package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import com.iase24.crazy_task_tracker_api.util.annotation.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResetPasswordResponse {

    @ValidUUID
    private String uuid;
}
