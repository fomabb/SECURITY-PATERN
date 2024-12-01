package com.iase24.crazy_task_tracker_api.security.service;

import com.iase24.crazy_task_tracker_api.businessapi.service.impl.DefaultEmailService;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignInEmployeeRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignInRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpEmployeeRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpRequest;
import com.iase24.crazy_task_tracker_api.security.dto.response.JwtAuthenticationResponse;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import com.iase24.crazy_task_tracker_api.security.entity.numentity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserServiceSecurity userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final DefaultEmailService emailService;

//=========================================Client=======================================================================

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        log.info("Начато выполнение сборки и сохранения пользователя в базу данных");
        var user = User.builder()
                .firstName(request.getFirstName())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        log.info("Пользователь сохранен в базу данных");
        userService.create(user);
        log.info("Начало генерации токена для пользователя");
        var jwt = jwtService.generateToken(user);
        if (jwt.isEmpty()) {
            log.warn("Не удалось сгенерировать токен для пользователя");
            throw new BusinessException("Токен для пользователя не сгенерирован");
        } else {
            try {
                log.info("Письмо с паролем отправлено пользователю {} на email: {}",
                        user.getFirstName(), user.getUsername());
                emailService.sendSimpleEmail(request.getEmail(), "Welcome %s"
                                .formatted(user.getFirstName()),
                        "Добро пожаловать на сайт https://test.iase24.com\nВаш пароль (%s) никому его не показывайте"
                                .formatted(request.getPassword()));
            } catch (MailException mailException) {
                log.error("Ошибка при отправке электронного письма..{}", (Object) mailException.getStackTrace());
                throw new BusinessException("Unable to send email");
            }
            log.info("Токен для пользователя сгенерирован");
            return new JwtAuthenticationResponse(jwt);
        }
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        log.info("Попытка авторизации пользователя");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());
        var jwt = jwtService.generateToken(user);
        log.info("Пользователь успешно авторизован");
        return new JwtAuthenticationResponse(jwt);
    }

//=========================================Employee=====================================================================

    /**
     * Регистрация работника
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUpEmployee(SignUpEmployeeRequest request) {
        log.info("Начато выполнение сборки и сохранения работника в базу данных");
        var user = User.builder()
                .firstName(request.getFirstName())
                .workEmail(request.getWorkEmail())
                .username(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_ADMIN)
                .build();
        log.info("Работник сохранен в базу данных");
        userService.create(user);
        log.info("Начало генерации токена работника");
        var jwt = jwtService.generateToken(user);
        if (jwt.isEmpty()) {
            log.warn("Не удалось сгенерировать токен для работника");
            throw new BusinessException("Токен для работника не сгенерирован");
        } else {
            log.info("Токен для работника сгенерирован");
            return new JwtAuthenticationResponse(jwt);
        }
    }

    /**
     * Аутентификация работника
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signInEmployee(SignInEmployeeRequest request) {
        log.info("Попытка авторизации работника");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));
        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getLogin());
        var jwt = jwtService.generateToken(user);
        log.info("Работник успешно авторизован");
        return new JwtAuthenticationResponse(jwt);
    }
}
