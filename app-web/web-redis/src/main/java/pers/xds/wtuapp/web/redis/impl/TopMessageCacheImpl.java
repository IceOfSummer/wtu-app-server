package pers.xds.wtuapp.web.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.web.redis.TopMessageCache;

import java.util.Collection;

/**
 * @author DeSen Xu
 * @date 2023-01-20 15:44
 */
@Component
public class TopMessageCacheImpl implements TopMessageCache {

    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_HASH_KEY = "TopMessageCache";

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Collection<Object> queryAllTopMessage() {
        return redisTemplate.opsForHash().entries(REDIS_HASH_KEY).values();
    }

    @Override
    public void removeTop(int id) {
        redisTemplate.opsForHash().delete(REDIS_HASH_KEY, String.valueOf(id));
    }

    @Override
    public void addTopMessage(int id, Object message) {
        redisTemplate.opsForHash().put(REDIS_HASH_KEY, String.valueOf(id), message);
    }
}
