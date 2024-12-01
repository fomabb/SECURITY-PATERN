package com.iase24.crazy_task_tracker_api.security.dto.request;

import com.iase24.crazy_task_tracker_api.util.annotation.CheckEmailIase24;
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
@Schema(description = "Запрос на регистрацию работника")
public class SignUpEmployeeRequest {

    @Schema(description = "Имя работника", example = "Андрей")
    @Size(min = 2, max = 50, message = "Имя пользователя должно содержать от 2 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String firstName;

    @Schema(description = "Адрес электронной почты", example = "worker@iase24.com")
    @Size(min = 5, max = 100, message = "Адрес электронной почты должен содержать от 5 до 100 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @CheckEmailIase24
    @Email(message = "Адрес электронной почты должен быть в формате worker@iase24.com")
    private String workEmail;

    @Schema(description = "Логин", example = "admin")
    @Size(min = 5, max = 100, message = "Адрес электронной почты должен содержать от 5 до 100 символов")
    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Schema(description = "Подтверждение пароля", example = "my_1secret1_password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String confirmPassword;
}
