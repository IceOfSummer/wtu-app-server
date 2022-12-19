package pers.xds.wtuapp.web.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.web.redis.EmailCaptchaCache;
import pers.xds.wtuapp.web.redis.bean.EmailData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author DeSen Xu
 * @date 2022-12-19 15:54
 */
@Component
public class EmailCaptchaCacheImpl implements EmailCaptchaCache {

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String REDIS_KEY_PREFIX = "EmailCaptchaCache:";

    private static final String HASH_KEY_EMAIL = "email";

    private static final String HASH_KEY_CAPTCHA = "captcha";

    private String getRedisKey(int uid) {
        return REDIS_KEY_PREFIX + uid;
    }



    @Override
    public void saveEmailCaptcha(int uid, String email, String captcha) {
        String key = getRedisKey(uid);
        HashMap<String, String> map = new HashMap<>(2);
        map.put(HASH_KEY_EMAIL, email);
        map.put(HASH_KEY_CAPTCHA, captcha);
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
    }

    @Override
    public EmailData getEmailCaptcha(int uid) {
        String key = getRedisKey(uid);
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        if (map.isEmpty()) {
            return null;
        }
        redisTemplate.delete(key);
        EmailData emailData = new EmailData();
        emailData.captcha = (String) map.get(HASH_KEY_CAPTCHA);
        emailData.email = (String) map.get(HASH_KEY_EMAIL);
        return emailData;
    }
}
