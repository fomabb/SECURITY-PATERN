package com.iase24.crazy_task_tracker_api.security.service;

import jakarta.mail.MessagingException;

import java.io.FileNotFoundException;

public interface EmailService {

    void sendSimpleEmail(final String toAddress, final String subject, final String message);

    void sendEmailWithAttachment(
            final String toAddress, final String subject, final String message, final String attachment)
            throws MessagingException, FileNotFoundException;
}
