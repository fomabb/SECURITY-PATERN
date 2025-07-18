package com.iase24.crazy_task_tracker_api.exceptionhandler.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ошибка API")
public record ErrorResponse(
        @JsonProperty("reason")
        @Schema(description = "HTTP статус код")
        String reason,

        @JsonProperty("message")
        @Schema(description = "Текст ошибки")
        String message
) {
}
