package com.iase24.crazy_task_tracker_api.businessapi.facade;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.UpdatePasswordClientRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.ClientResetPasswordResponse;
import com.iase24.crazy_task_tracker_api.businessapi.service.ClientService;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientFacade {

    private final ClientService clientService;

    public ClientResetPasswordResponse resetPasswordClient(UUID clientId, UpdatePasswordClientRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException("Пароль не совпадает с заданным Вами паролем");
        } else {
            return clientService.resetPasswordClient(clientId, request);
        }
    }
}
