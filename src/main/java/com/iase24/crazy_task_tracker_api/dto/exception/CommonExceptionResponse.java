package com.iase24.crazy_task_tracker_api.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonExceptionResponse {
    private LocalDateTime timestamp;

    private String exceptionClass;

    private String message;
}
