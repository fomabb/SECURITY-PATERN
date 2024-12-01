package com.iase24.crazy_task_tracker_api.dto.request;

import com.iase24.crazy_task_tracker_api.util.annotation.ValidUUID;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Dto для оборачивания строкового представления UUID",
        type = "String", example = "c9c217bf-62eb-4501-87f2-8dc6ea3abc3a",
        format = "UUID")
public class UserUUIDRequest {

    @ValidUUID
    private String userId;
}
