package pers.xds.wtuapp.web.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.web.redis.CounterCache;
import pers.xds.wtuapp.web.redis.common.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author DeSen Xu
 * @date 2022-12-04 21:23
 */
@Component
public class CounterCacheImpl implements CounterCache {

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public int getInvokeCount(String key, Duration duration) {
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) {
            redisTemplate.opsForValue().set(
                    key,
                    0,
                    duration.countDuration(),
                    TimeUnit.MILLISECONDS
            );
            return 0;
        }
        return (int) o;
    }

    @Override
    public void increaseInvokeCount(String key) {
        redisTemplate.opsForValue().increment(key);
    }

}
