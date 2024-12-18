package com.iase24.crazy_task_tracker_api.businessapi.controller;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ResetPasswordClientRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.UpdatePasswordClientRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.ClientResetPasswordResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.LocationClientResponse;
import com.iase24.crazy_task_tracker_api.businessapi.facade.ClientFacade;
import com.iase24.crazy_task_tracker_api.businessapi.service.ClientService;
import com.iase24.crazy_task_tracker_api.dto.response.GetIpClientResponse;
import com.iase24.crazy_task_tracker_api.security.dto.response.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static com.iase24.crazy_task_tracker_api.filter.IpLoggingFilter.getClientIP;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Клиентский API", description = "Интерфейс для бизнес логики")
@SecurityRequirement(name = "bearerAuth")
public class ClientController {

    private final ClientService clientService;
    private final ClientFacade clientFacade;

//===========================Section User===============================================================================

    @PostMapping("/reset-password")
    public ResponseEntity<JwtAuthenticationResponse> generateTokenForResetPassword(@RequestBody ResetPasswordClientRequest request) {
        log.info("Получен запрос на генерацию токена, для обновления пароля");
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.generateTokenForResetPassword(request));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<ClientResetPasswordResponse> resetPasswordClient(
            @RequestParam String token,
            @RequestBody UpdatePasswordClientRequest request
    ) {
        log.info("Получен запрос на восстановление пароля");
        return ResponseEntity.ok(clientFacade.resetPasswordClient(token, request));
    }

    @GetMapping("/real-ip")
    public ResponseEntity<GetIpClientResponse> getClientLocation(HttpServletRequest request) {
        return ResponseEntity.ok(GetIpClientResponse.builder().ip(getClientIP(request)).build());
    }

    @GetMapping("/external-ip")
    public String getExternalIp() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://api.ipify.org?format=json", String.class);
    }

    @GetMapping("/find-city")
    public ResponseEntity<LocationClientResponse> findCity(HttpServletRequest request) {
        String ipAddressClient = getClientIP(request);
        LocationClientResponse cityClientLocation = clientService.getCityByIpClient(ipAddressClient);
        return ResponseEntity.ok(cityClientLocation);
    }
}
