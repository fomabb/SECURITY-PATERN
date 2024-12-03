package com.iase24.crazy_task_tracker_api.businessapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Запрос на смену пароля")
public class UpdatePasswordClientRequest {

    @Schema(description = "Новый пароль", example = "new_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String newPassword;

    @Schema(description = "Подтверждение пароля", example = "new_1secret1_password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String confirmPassword;
}
