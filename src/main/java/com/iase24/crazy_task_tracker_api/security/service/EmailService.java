package com.iase24.crazy_task_tracker_api.security.service;

import jakarta.mail.MessagingException;

import java.io.FileNotFoundException;

public interface EmailService {

    void sendSimpleEmail(final String toAddress, final String subject, final String message);

    void sendEmailWithAttachment(
            final String toAddress, final String subject, final String message, final String attachment)
            throws MessagingException, FileNotFoundException;

    /**
     * Отправляет электронное письмо с кодом восстановления на указанный адрес электронной почты.
     *
     * @param email адрес электронной почты получателя
     * @param code  код восстановления
     */
    void sendRecoveryCodeEmail(String email, char[] code);

    /**
     * Отправляет электронное письмо с новым паролем на указанный адрес электронной почты.
     *
     * @param email    адрес электронной почты получателя
     * @param password новый пароль
     */
    void sendNewPasswordEmail(String email, char[] password);

    /**
     * Отправляет электронное письмо с временным паролем на указанный адрес электронной почты.
     *
     * @param email    адрес электронной почты получателя
     * @param password временный пароль
     */
    void sendTemporaryPasswordEmail(String email, char[] password);
}
