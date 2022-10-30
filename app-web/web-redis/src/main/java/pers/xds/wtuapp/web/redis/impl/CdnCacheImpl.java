package pers.xds.wtuapp.web.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.web.redis.CdnCache;

/**
 * @author DeSen Xu
 * @date 2022-09-11 22:59
 */
@Component
public class CdnCacheImpl implements CdnCache {

    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY_PREFIX = "cdn:";

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public int getKeyGenerateTimes(int userId) {
        Long increment = redisTemplate.opsForValue().increment(getRedisKey(userId));
        if (increment == null) {
            return 0;
        }
        return Math.toIntExact(increment);
    }

    private String getRedisKey(int userId) {
        return REDIS_KEY_PREFIX + userId;
    }
}
