package com.iase24.crazy_task_tracker_api.security.facade;

import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpEmployeeRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpRequest;
import com.iase24.crazy_task_tracker_api.security.dto.response.JwtAuthenticationResponse;
import com.iase24.crazy_task_tracker_api.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthenticationService authenticationService;

//=========================================Client=======================================================================

    public JwtAuthenticationResponse signUpFacade(SignUpRequest request) {

        if (request.getPassword().equals(request.getConfirmPassword())) {
            return authenticationService.signUp(request);
        } else {
            throw new BusinessException("Пароль не совпадает с заданным Вами паролем");
        }
    }

//=========================================Employee=====================================================================

    public JwtAuthenticationResponse signUpEmployeeFacade(SignUpEmployeeRequest request) {

        if (request.getPassword().equals(request.getConfirmPassword())) {
            return authenticationService.signUpEmployee(request);
        } else {
            throw new BusinessException("Пароль не совпадает с заданным Вами паролем");
        }
    }
}
