package pers.xds.wtuapp.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

/**
 * @author DeSen Xu
 * @date 2022-09-02 18:02
 */
@Configuration
public class RedisSessionConfig {

    @Bean
    RedisIndexedSessionRepository redisSessionRepository(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisIndexedSessionRepository(redisTemplate);
    }

}
