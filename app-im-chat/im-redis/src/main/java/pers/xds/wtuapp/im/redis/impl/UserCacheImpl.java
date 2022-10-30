package pers.xds.wtuapp.im.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.database.UserMapper;
import pers.xds.wtuapp.im.database.bean.User;
import pers.xds.wtuapp.im.redis.UserCache;

import java.util.concurrent.TimeUnit;

/**
 * @author HuPeng
 * @date 2022-10-30 15:10
 */
@Component
public class UserCacheImpl implements UserCache {

    private RedisTemplate<Object, Object> redisTemplate;

    public static final String TRUE = "1";

    public static final String FALSE = "0";

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String KEY_PREFIX = "user:";

    @Override
    public boolean isUserNotExist(int uid) {
        final int expire = 1;
        String key = getUserExistKey(uid);
        Object exist = redisTemplate.opsForValue().get(key);
        if (exist == null) {
            User user = userMapper.selectById(uid);
            if (user == null) {
                redisTemplate.opsForValue().set(key, TRUE, expire, TimeUnit.HOURS);
                return false;
            } else {
                redisTemplate.opsForValue().set(key, FALSE, expire, TimeUnit.HOURS);
                return true;
            }
        }
        return exist.equals(TRUE);
    }

    private String getUserExistKey(int uid) {
        return KEY_PREFIX + "notExist:" + uid;
    }
}
