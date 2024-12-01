package com.iase24.crazy_task_tracker_api.security.facade;

import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpEmployeeRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpRequest;
import com.iase24.crazy_task_tracker_api.security.dto.response.JwtAuthenticationResponse;
import com.iase24.crazy_task_tracker_api.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthenticationService authenticationService;

//=========================================Client=======================================================================

    public JwtAuthenticationResponse signUpFacade(SignUpRequest request) {
        log.info("Начало проверки подтверждения пароля пользователя");
        if (request.getPassword().equals(request.getConfirmPassword())) {
            log.info("Пароль пользователя подтвержден успешно");
            return authenticationService.signUp(request);
        } else {
            log.warn("Пароль пользователя не совпадает с введенным изначально");
            throw new BusinessException("Пароль не совпадает с заданным Вами паролем");
        }
    }

//=========================================Employee=====================================================================

    public JwtAuthenticationResponse signUpEmployeeFacade(SignUpEmployeeRequest request) {
        log.info("Начало проверки подтверждения пароля работника");
        if (request.getPassword().equals(request.getConfirmPassword())) {
            log.info("Пароль работника подтвержден успешно");
            return authenticationService.signUpEmployee(request);
        } else {
            log.warn("Пароль работника не совпадает с введенным изначально");
            throw new BusinessException("Пароль не совпадает с заданным Вами паролем");
        }
    }
}
