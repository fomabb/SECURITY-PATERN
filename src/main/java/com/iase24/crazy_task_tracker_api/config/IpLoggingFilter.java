package com.iase24.crazy_task_tracker_api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class IpLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String clientIp = IpUtils.getClientIP(request);
        System.out.println("Client IP: " + clientIp);
        filterChain.doFilter(request, response);
    }

    public static class IpUtils {
        public static String getClientIP(HttpServletRequest request) {
            String ipAddress = request.getHeader("X-Forwarded-For");

            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }

            // Логируем заголовки для диагностики
            System.out.println("X-Forwarded-For: " + request.getHeader("X-Forwarded-For"));
            System.out.println("Remote Address: " + request.getRemoteAddr());
            return ipAddress;
        }
    }
}
