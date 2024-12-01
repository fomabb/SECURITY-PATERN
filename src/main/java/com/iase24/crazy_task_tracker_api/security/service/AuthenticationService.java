package com.iase24.crazy_task_tracker_api.security.service;

import com.iase24.crazy_task_tracker_api.security.dto.request.SignInEmployeeRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignInRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpEmployeeRequest;
import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpRequest;
import com.iase24.crazy_task_tracker_api.security.dto.response.JwtAuthenticationResponse;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import com.iase24.crazy_task_tracker_api.security.entity.numentity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserServiceSecurity userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

//=========================================Client=======================================================================

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .firstName(request.getFirstName())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        var jwt = jwtService.generateToken(user);
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

        var user = User.builder()
                .firstName(request.getFirstName())
                .username(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_ADMIN)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация работника
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signInEmployee(SignInEmployeeRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getLogin());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
