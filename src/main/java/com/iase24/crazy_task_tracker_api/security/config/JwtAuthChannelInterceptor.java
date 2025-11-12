package com.iase24.crazy_task_tracker_api.security.config;

import com.iase24.crazy_task_tracker_api.security.service.JwtServiceSecurity;
import com.iase24.crazy_task_tracker_api.security.service.UserServiceSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthChannelInterceptor implements ChannelInterceptor {

    private final JwtServiceSecurity jwtService;
    private final UserServiceSecurity userService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            log.debug("WebSocket Authorization header: {}", authHeader);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwt = authHeader.substring(7);

                // Используем методы из вашего JwtServiceSecurity
                String username = jwtService.extractUserEmail(jwt);

                if (username != null) {
                    // Используем ваш UserServiceSecurity для загрузки UserDetails
                    UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

                    // Проверяем валидность токена с помощью вашего сервиса
                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        // Прикрепляем аутентифицированного пользователя к WebSocket сессии
                        accessor.setUser(authentication);
                        log.info("User {} successfully authenticated for WebSocket session.", username);
                    }
                }
            }
        }
        return message;
    }
}
