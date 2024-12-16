package com.iase24.crazy_task_tracker_api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserResponse {

    private UUID uuid;
    private String firstName;
    private String email;
    private String password;
    private String role;

    @JsonFormat(pattern = "dd-MM-YYYY hh:mm")
    private LocalDateTime createDateTimeUser;
}
