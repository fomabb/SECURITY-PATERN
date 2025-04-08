package com.iase24.crazy_task_tracker_api.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Getter
@Setter
@RequiredArgsConstructor
public class JedisConfig {

//    @Value("${spring.data.redis.password}")
//    private static String redisPassword;

    public static JedisPool jedisPool;

    static {
        // Конфигурация пула
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128); // Максимальное количество соединений
        poolConfig.setMaxIdle(128); // Максимальное количество "бездействующих" соединений
        poolConfig.setMinIdle(16); // Минимальное количество "бездействующих" соединений
        poolConfig.setTestOnBorrow(true); // Проверка соединения при получении из пула
        poolConfig.setTestOnReturn(true); // Проверка соединения при возвращении в пул
        poolConfig.setTestWhileIdle(true); // Проверка соединений во время простоя

        // Инициализация пула
        jedisPool = new JedisPool(poolConfig, "localhost", 6379, 3000, null);
    }

    public static void closePool() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}
