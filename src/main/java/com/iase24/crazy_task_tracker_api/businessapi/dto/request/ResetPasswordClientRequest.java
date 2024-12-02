package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordClientRequest {

    @Schema(description = "Адрес электронной почты", example = "vikabb123@gmail.com")
    @Size(min = 5, max = 100, message = "Адрес электронной почты должен содержать от 5 до 100 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате user@gmail.com")
    private String email;
}
