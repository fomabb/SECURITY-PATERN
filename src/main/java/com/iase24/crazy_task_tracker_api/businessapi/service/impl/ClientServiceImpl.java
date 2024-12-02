package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ResetPasswordClientRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.UpdatePasswordClientRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.ClientResetPasswordResponse;
import com.iase24.crazy_task_tracker_api.businessapi.service.ClientService;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import com.iase24.crazy_task_tracker_api.security.dto.response.JwtAuthenticationResponse;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import com.iase24.crazy_task_tracker_api.security.repository.UserRepository;
import com.iase24.crazy_task_tracker_api.security.service.EmailSenderServiceImpl;
import com.iase24.crazy_task_tracker_api.security.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final EmailSenderServiceImpl emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationResponse generateTokenForResetPassword(ResetPasswordClientRequest request) {
        log.info("Начало выполнения генерации токена по запросу {}", request.getEmail());
        var bodyClientResetPassword = ResetPasswordClientRequest.builder()
                .email(request.getEmail())
                .build();
        User user = userRepository.findByUsername(bodyClientResetPassword.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Client with email: %s not found"
                        .formatted(request.getEmail())));
        log.info("Начало генерации токена клиента");
        var jwt = jwtService.generateToken(user);
        if (jwt.isEmpty()) {
            log.warn("Не удалось сгенерировать токен для работника");
            throw new BusinessException("Токен для работника не сгенерирован");
        } else {
            try {
                log.info("Письмо с паролем отправлено пользователю {} на email: {}",
                        user.getFirstName(), user.getUsername());
                emailService.sendSimpleEmail(user.getUsername(), "Welcome %s"
                                .formatted(user.getFirstName()),
                        "Для смены пароля перейдите по данной ссылке: https://a-sber-web-dev.astondevs.ru/reset/"
                                + jwt + " ");
            } catch (MailException mailException) {
                log.error("Ошибка при отправке электронного письма..{}", (Object) mailException.getStackTrace());
                throw new BusinessException("Unable to send email");
            }
            log.info("Токен для клиента сгенерирован");
            return new JwtAuthenticationResponse(jwt);
        }
    }

    @Override
    public ClientResetPasswordResponse resetPasswordClient(UUID clientId, UpdatePasswordClientRequest request) {
        log.info("Начало выполнения восстановления пароля");
        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Email not found"));
        var resetPassword = UpdatePasswordClientRequest.builder()
                .newPassword(passwordEncoder.encode(request.getNewPassword()))
                .build();
        try {
            log.info("Письмо с паролем отправлено клиенту {} на email: {}",
                    user.getFirstName(), user.getUsername());
            emailService.sendSimpleEmail(user.getUsername(), "Welcome %s"
                            .formatted(user.getFirstName()),
                    "Восстановление пароля прошло успешно! Ваш новый пароль (%s), никому его не показывайте. Удачного дня!"
                            .formatted(request.getNewPassword()));
        } catch (MailException mailException) {
            log.error("Ошибка при отправке клиенту электронного письма..{}", (Object) mailException.getStackTrace());
            throw new BusinessException("Unable to send email");
        }
        user.setPassword(resetPassword.getNewPassword());
        userRepository.save(user);
        return new ClientResetPasswordResponse(user.getId().toString());
    }
}
