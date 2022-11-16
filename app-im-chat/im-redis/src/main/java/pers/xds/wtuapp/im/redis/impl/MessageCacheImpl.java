package pers.xds.wtuapp.im.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.redis.MessageCache;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-10 16:41
 */
@Component
public class MessageCacheImpl implements MessageCache {

    /**
     * 消息过期时间
     */
    private static final Duration MESSAGE_EXPIRE_TIME = Duration.ofHours(1);

    private static final String KEY_PREFIX = "message.cache:";

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier("redisJsonTemplate")
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private HashOperations<String, String, Message> opsForHash() {
        return redisTemplate.opsForHash();
    }

    private String getMessageKey(int uid) {
        return KEY_PREFIX + uid;
    }

    @Override
    public List<Message> queryCachedMessage(int uid, List<String> msgIds) {
        String messageKey = getMessageKey(uid);
        return opsForHash().multiGet(messageKey, new ArrayList<>(msgIds));
    }

    @Override
    @Async
    public void saveMessage(Message message, int receiveId, int sendId) {
        String messageKey = getMessageKey(message.getUid());
        message.setMsgId(receiveId);
        message.setType(Message.RECEIVE);
        opsForHash().put(messageKey, String.valueOf(receiveId), message);
        redisTemplate.expire(messageKey, MESSAGE_EXPIRE_TIME);
        // swap
        messageKey = getMessageKey(message.getTo());
        message.setMsgId(sendId);
        int temp = message.getUid();
        message.setUid(message.getTo());
        message.setTo(temp);
        message.setType(Message.SEND);
        opsForHash().put(messageKey, String.valueOf(sendId), message);
    }

}
