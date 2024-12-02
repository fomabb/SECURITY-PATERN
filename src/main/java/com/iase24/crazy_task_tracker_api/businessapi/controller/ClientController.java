package com.iase24.crazy_task_tracker_api.businessapi.controller;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ResetPasswordClientRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.UpdatePasswordClientRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.ClientResetPasswordResponse;
import com.iase24.crazy_task_tracker_api.businessapi.facade.ClientFacade;
import com.iase24.crazy_task_tracker_api.businessapi.service.ClientService;
import com.iase24.crazy_task_tracker_api.security.dto.response.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Клиентский API", description = "Интерфейс для бизнес логики")
public class ClientController {

    private final ClientService clientService;
    private final ClientFacade clientFacade;

    @PostMapping("/reset-password")
    public ResponseEntity<JwtAuthenticationResponse> generateTokenForResetPassword(@RequestBody ResetPasswordClientRequest request) {
        log.info("Получен запрос на генерацию токена, для обновления пароля");
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.generateTokenForResetPassword(request));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<ClientResetPasswordResponse> resetPasswordClient(
            @RequestHeader("X-Client-Id") UUID clientId,
            @RequestBody UpdatePasswordClientRequest request
    ) {
        log.info("Получен запрос на восстановление пароля");
        return ResponseEntity.ok(clientFacade.resetPasswordClient(clientId, request));
    }
}
