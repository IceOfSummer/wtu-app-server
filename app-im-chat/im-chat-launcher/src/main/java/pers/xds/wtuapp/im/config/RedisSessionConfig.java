package pers.xds.wtuapp.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

/**
 * @author DeSen Xu
 * @date 2022-09-02 18:02
 */
@Configuration
public class RedisSessionConfig {

    @Bean
    RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    RedisIndexedSessionRepository redisSessionRepository(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisIndexedSessionRepository(redisTemplate);
    }

}
