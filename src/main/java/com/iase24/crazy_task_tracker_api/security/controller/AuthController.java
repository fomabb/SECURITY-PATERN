package com.iase24.crazy_task_tracker_api.security.controller;

import com.iase24.crazy_task_tracker_api.security.dto.request.SignInEmployeeRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignInRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpEmployeeRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpRequest;
import com.iase24.crazy_task_tracker_api.security.dto.response.JwtAuthenticationResponse;
import com.iase24.crazy_task_tracker_api.security.facade.AuthFacade;
import com.iase24.crazy_task_tracker_api.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Аутентификация", description = "Интерфейс для логики аутентификации")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final AuthFacade authFacade;

//=========================================Client=======================================================================

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        log.info("Получен запрос на регистрацию нового пользователя {} с email: {}",
                request.getFirstName(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(authFacade.signUpFacade(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        log.info("Получен запрос на авторизацию пользователя");
        return authenticationService.signIn(request);
    }

//=========================================Employee=====================================================================

    @Operation(summary = "Регистрация работника")
    @PostMapping("/sign-up/employee")
    public ResponseEntity<JwtAuthenticationResponse> signUpEmployee(@RequestBody @Valid SignUpEmployeeRequest request) {
        log.info("Получен запрос на регистрацию нового работника {} с email: {}",
                request.getFirstName(), request.getWorkEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(authFacade.signUpEmployeeFacade(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in/employee")
    public JwtAuthenticationResponse signInEmployee(@RequestBody @Valid SignInEmployeeRequest request) {
        log.info("Получен запрос на авторизацию работника");
        return authenticationService.signInEmployee(request);
    }
}
