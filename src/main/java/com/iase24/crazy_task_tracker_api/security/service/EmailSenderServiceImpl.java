package com.iase24.crazy_task_tracker_api.security.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    @Override
    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment)
            throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Purchase Order", file);
        emailSender.send(mimeMessage);
    }

    /**
     * Отправляет электронное письмо с кодом восстановления на указанный адрес электронной почты.
     *
     * @param email адрес электронной почты получателя
     * @param code  код восстановления
     */
    @Override
    public void sendRecoveryCodeEmail(String email, char[] code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Код для сброса пароля учетной записи");
        message.setText("Ваш проверочный код: " + new String(code) + "\nКод действителен в течение 10 минут");
        message.setFrom("asber.aston.as@yandex.ru");

        sendEmailWithLogging(email, message);
    }

    /**
     * Отправляет электронное письмо с новым паролем на указанный адрес электронной почты.
     *
     * @param email    адрес электронной почты получателя
     * @param password новый пароль
     */
    @Override
    public void sendNewPasswordEmail(String email, char[] password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Новый пароль для авторизации");
        message.setText("Ваш новый пароль: " + new String(password));
        message.setFrom("asber.aston.as@yandex.ru");

        sendEmailWithLogging(email, message);
    }

    /**
     * Отправляет электронное письмо с временным паролем на указанный адрес электронной почты.
     *
     * @param email    адрес электронной почты получателя
     * @param password временный пароль
     */
    @Override
    public void sendTemporaryPasswordEmail(String email, char[] password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Временный пароль для авторизации");
        message.setText("Ваш временный пароль: " + new String(password));
        message.setFrom("asber.aston.as@yandex.ru");

        sendEmailWithLogging(email, message);
    }

    /**
     * Отправляет электронное письмо с логированием результата.
     *
     * @param email   адрес электронной почты получателя
     * @param message сообщение для отправки
     */
    private void sendEmailWithLogging(String email, SimpleMailMessage message) {
        try {
            emailSender.send(message);
            log.info("Email sent successfully to {}", email);
        } catch (Exception e) {
            log.info("Sending email to: {}", email + message);
            log.error("Failed to send email to {}", email, e);
        }
    }
}
