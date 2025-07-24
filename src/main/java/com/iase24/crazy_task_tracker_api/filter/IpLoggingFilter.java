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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

//    public static String getClientIP(HttpServletRequest request) {
//        String forwardHeader = request.getHeader("X-Forwarded-For");
//        if (forwardHeader != null) {
//            return forwardHeader.split(",")[0];
//        }
//        return request.getRemoteAddr();
//    }

    public static String getClientIP(HttpServletRequest request) {
        // Порядок проверки заголовков важен - от наиболее надежных к менее надежным
        List<String> headersToCheck = Arrays.asList(
                "X-Forwarded-For",       // Стандартный заголовок для прокси
                "Forwarded",             // RFC 7239 стандарт
                "X-Real-IP",            // Nginx и другие
                "CF-Connecting-IP",     // Cloudflare
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA"
        );

        // Проверяем заголовки в порядке приоритета
        Optional<String> ip = headersToCheck.stream()
                .map(request::getHeader)
                .filter(header -> header != null && !header.isEmpty() && !"unknown".equalsIgnoreCase(header))
                .findFirst();

        // Обрабатываем найденный IP
        if (ip.isPresent()) {
            String clientIp = ip.get().split(",")[0].trim();
            // Дополнительная валидация IP
            if (isValidIP(clientIp)) {
                return clientIp;
            }
        }

        // Финальный fallback
        return request.getRemoteAddr();
    }

    // Валидация IP адреса
    private static boolean isValidIP(String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            // Проверяем IPv4 и IPv6
            String[] parts = ip.split("\\.");
            if (parts.length == 4) { // IPv4
                for (String part : parts) {
                    int num = Integer.parseInt(part);
                    if (num < 0 || num > 255) {
                        return false;
                    }
                }
                return true;
            }

            // Для IPv6 можно добавить дополнительные проверки
            return ip.contains(":"); // Простая проверка IPv6
        } catch (Exception e) {
            return false;
        }
    }
}
