package pers.xds.wtuapp.web.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.web.redis.TopMessageCache;

import java.util.Set;

/**
 * @author DeSen Xu
 * @date 2023-01-20 15:44
 */
@Component
public class TopMessageCacheImpl implements TopMessageCache {

    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY = "TopMessageCache";

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Set<Integer> queryAllTopMessage() {
        Object members = redisTemplate.opsForSet().members(REDIS_KEY);
        return (Set<Integer>) members;
    }

    @Override
    public void removeTop(int id) {
        redisTemplate.opsForSet().remove(REDIS_KEY, id);
    }

    @Override
    public void addTopMessage(int id) {
        redisTemplate.opsForSet().add(REDIS_KEY, id);
    }
}
