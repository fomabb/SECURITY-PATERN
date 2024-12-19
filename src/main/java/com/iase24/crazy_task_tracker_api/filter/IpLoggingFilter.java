package com.iase24.crazy_task_tracker_api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class IpLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String clientIp = getClientIP(request);
        System.out.println("Client IP: " + clientIp);
        filterChain.doFilter(request, response);
    }

    public static String getClientIP(HttpServletRequest request) {
        String forwardHeader = request.getHeader("X-Forwarded-For");
        if (forwardHeader != null) {
            return forwardHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
