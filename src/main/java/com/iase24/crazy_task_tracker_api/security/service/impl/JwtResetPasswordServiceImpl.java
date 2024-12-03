package com.iase24.crazy_task_tracker_api.security.service.impl;

import com.iase24.crazy_task_tracker_api.security.entity.User;
import com.iase24.crazy_task_tracker_api.security.service.JwtResetPasswordService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class JwtResetPasswordServiceImpl implements JwtResetPasswordService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Override
    @Deprecated
    public String generateResetPasswordToken(User user) {
//        long expirationTime = 15 * 60 * 1000; // 15 минут в миллисекундах
        long expirationTime = 60 * 1000; // 1 минута в миллисекундах
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(user.getUsername()) // Используем email как идентификатор
                .setIssuedAt(now) // Дата выпуска токена
                .setExpiration(expiryDate) // Указываем время истечения
                .claim("type", "password_reset") // Дополнительный claim для типа токена
                .signWith(Keys.hmacShaKeyFor(jwtSigningKey.getBytes()), SignatureAlgorithm.HS512) // Ваш секретный ключ
                .compact();
    }

    @Override
    @Deprecated
    public boolean validateResetPasswordToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSigningKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // Проверяем тип токена
            String tokenType = claims.get("type", String.class);
            return "password_reset".equals(tokenType); // Неверный тип токена
            // Токен валиден
        } catch (ExpiredJwtException e) {
            log.error("Срок действия токена истек: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Недопустимый токен: {}", e.getMessage());
            return false;
        }
    }
}
