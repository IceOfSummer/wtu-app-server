package pers.xds.wtuapp.im.redis;

import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.database.bean.Message;

import java.util.List;

/**
 * 消息缓存，用于同步
 * @author DeSen Xu
 * @date 2022-11-10 16:36
 */
@Component
public interface MessageCache {

    /**
     * 获取缓存的消息
     * @param uid 接受者
     * @param msgIds 消息id
     * @return 缓存的消息, 不一定会全部获取
     */
    List<Message> queryCachedMessage(int uid, List<String> msgIds);

    /**
     * 将消息保存到缓存中
     * @param message 消息
     */
    void saveMessage(Message message);

}
