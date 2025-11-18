package com.iase24.crazy_task_tracker_api.security.config;

import com.iase24.crazy_task_tracker_api.security.service.UserServiceSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.stream.Collectors;

import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.ACTUATOR_URL;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.ADMIN_API;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.AUTH_API;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.CHAT_API;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.CLIENT_API;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.DELIVERIES_API;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.ENDPOINT_URL;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.MOVIES_API;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.NEWS_API;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.OFFICES_API;
import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.WEB_SOCKET_WS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserServiceSecurity userService;

    private static final List<String> PERMIT_ALL = List.of(
            AUTH_API, ACTUATOR_URL, NEWS_API, CLIENT_API, MOVIES_API, DELIVERIES_API, OFFICES_API, WEB_SOCKET_WS
    );

    private static final List<String> USER_ACCESS = List.of(
            CHAT_API
    );

    private static final List<String> ADMIN_ACCESS = List.of(
            ENDPOINT_URL, ADMIN_API
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // Своего рода отключение CORS (разрешение запросов со всех доменов)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                // Настройка доступа к конечным точкам
                .authorizeHttpRequests(request -> request
                        /*
                          Можно указать конкретный путь, * - 1 уровень вложенности, ** - любое количество уровней вложенности
                          permitAll - Эндпоинт доступен всем пользователям, и авторизованным и нет
                          authenticated - Только авторизованные пользователи
                          hasRole - Пользователь должен иметь конкретную роль, и, соответственно быть авторизованным
                          hasAnyRole - Должен иметь одну из перечисленных ролей (не представлено в коде)
                         */
                        .requestMatchers(appendAllPattern(PERMIT_ALL).toArray(new String[]{})).permitAll()
                        .requestMatchers(appendAllPattern(USER_ACCESS).toArray(new String[]{})).hasRole("USER")
                        .requestMatchers(appendAllPattern(ADMIN_ACCESS).toArray(new String[]{})).hasRole("ADMIN")
                        .requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/graphql/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    private List<String> appendAllPattern(List<String> urls) {
        return urls.stream().map(s -> s + "/**").collect(Collectors.toList());
    }
}