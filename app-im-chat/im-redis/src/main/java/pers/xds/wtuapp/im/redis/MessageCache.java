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
     * @return 缓存的消息, 不一定会全部获取, 但返回的数组长度一定和msgIds相等，没有查到的内容为null
     */
    List<Message> queryCachedMessage(int uid, List<String> msgIds);

    /**
     * 将消息保存到缓存中
     * <p>
     * 该方法应为<b>异步操作</b>
     * @param message 消息, {@link Message#getUid()} 应该为消息接收方的uid, {@link Message#getType()}应该为{@link Message#RECEIVE}
     * @param receiveId 接收方消息id
     * @param sendId 发送方消息id
     */
    void saveMessage(Message message, int receiveId, int sendId);

}
