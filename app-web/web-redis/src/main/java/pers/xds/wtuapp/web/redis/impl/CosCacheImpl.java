package pers.xds.wtuapp.web.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.web.redis.CosCache;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author DeSen Xu
 * @date 2022-09-11 22:59
 */
@Component
public class CosCacheImpl implements CosCache {

    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY_PREFIX = "cdn:";

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public int getKeyGenerateTimes(int userId) {
        String key = getRedisKey(userId);
        Long increment = redisTemplate.opsForValue().increment(key);
        if (increment == null) {
            // set
            Calendar instance = Calendar.getInstance();
            instance.set(
                    instance.get(Calendar.YEAR),
                    instance.get(Calendar.MONTH),
                    instance.get(Calendar.DATE),
                    23,
                    59,
                    59
            );
            redisTemplate.opsForValue().set(
                    key,
                    "1",
                    instance.getTimeInMillis() - System.currentTimeMillis(),
                    TimeUnit.MILLISECONDS
            );
            return 0;
        }
        return (int) (increment - 1);
    }

    private String getRedisKey(int userId) {
        return REDIS_KEY_PREFIX + userId;
    }
}
