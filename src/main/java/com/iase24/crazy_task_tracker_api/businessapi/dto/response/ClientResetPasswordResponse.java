package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import com.iase24.crazy_task_tracker_api.util.annotation.ValidUUID;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Ответ по смене пароля")
public class ClientResetPasswordResponse {

    @ValidUUID
    @Schema(description = "Возврат UUID пользователя", example = "(8-4-4-4-12): 53ac777f-fa99-4e47-875d-00782a0b8049")
    private String uuid;

    @Schema(description = "Сообщение о результате смены пароля",
            example = "Пароль успешно восстановлен и изменен! На вашу почту отправлено сообщение с новым паролем")
    private String message;

    public ClientResetPasswordResponse(String uuid) {
        this.uuid = uuid;
        this.message = "Пароль успешно восстановлен и изменен! На вашу почту отправлено сообщение с новым паролем";
    }
}
