package pers.xds.wtuapp.web.database.common;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author DeSen Xu
 * @date 2023-01-09 22:54
 */
public class RedisCache implements Cache {

    private final String id;

    /**
     * 由外部设置
     * @see pers.xds.wtuapp.web.database.DatabaseConfig#setRedisTemplate(RedisTemplate)
     */
    private static RedisTemplate<String, Object> redisTemplate;

    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id can not be null");
        }
        this.id = id;

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        redisTemplate.opsForHash().put(id, key.toString(), value);
    }

    @Override
    public Object getObject(Object key) {
        return redisTemplate.opsForHash().get(id, key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        return redisTemplate.opsForHash().delete(id, key.toString());
    }

    @Override
    public void clear() {
        redisTemplate.delete(id);
    }

    @Override
    public int getSize() {
        return Math.toIntExact(redisTemplate.opsForHash().size(id));
    }

    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisCache.redisTemplate = redisTemplate;
    }
}
