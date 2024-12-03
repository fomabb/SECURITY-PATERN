package com.iase24.crazy_task_tracker_api.security.service;

import com.iase24.crazy_task_tracker_api.security.entity.User;

public interface JwtResetPasswordService {

    String generateResetPasswordToken(User user);

    boolean validateResetPasswordToken(String token);
}
