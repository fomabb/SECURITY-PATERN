package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import com.iase24.crazy_task_tracker_api.util.annotation.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClientResetPasswordResponse {

    @ValidUUID
    private String uuid;

    private String message;

    public ClientResetPasswordResponse(String uuid) {
        this.uuid = uuid;
        this.message = "Пароль успешно восстановлен и изменен! На вашу почту отправлено сообщение с новым паролем";
    }
}
