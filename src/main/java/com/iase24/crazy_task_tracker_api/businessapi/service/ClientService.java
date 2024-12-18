package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ResetPasswordClientRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.UpdatePasswordClientRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.ClientResetPasswordResponse;
import com.iase24.crazy_task_tracker_api.security.dto.response.JwtAuthenticationResponse;

public interface ClientService {

    JwtAuthenticationResponse generateTokenForResetPassword(ResetPasswordClientRequest request);

    ClientResetPasswordResponse resetPasswordClient(String token, UpdatePasswordClientRequest request);

    String getGeoLocation(String ip);
}
