package pers.xds.wtuapp.im.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.im.database.UserMessageMapper;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.redis.MessageCache;
import pers.xds.wtuapp.im.service.ChatService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-03 16:41
 */
@Service
public class ChatServiceImpl implements ChatService {

    private UserMessageMapper userMessageMapper;

    private MessageCache messageCache;


    @Autowired
    public void setMessageCache(MessageCache messageCache) {
        this.messageCache = messageCache;
    }

    @Autowired
    public void setUserMessageMapper(UserMessageMapper userMessageMapper) {
        this.userMessageMapper = userMessageMapper;
    }


    /**
     * 先保存到数据库，然后保存到redis用作消息同步
     * 对于同一个to参数，并发调用可能会存在问题
     * @param message 消息对象。该操作会修改该对象的{@link Message#type}和{@link Message#msgId}属性
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveMessage(Message message, boolean sync) {
        // 理论上应该要检查用户是否存在，但由于是俩数据库，实现起来有点麻烦
        int senderId = message.getUid();
        int receiverId = message.getTo();
        message.setType(Message.SEND);
        userMessageMapper.insert(message);
        Integer msgId = message.getMsgId();
        message.setType(Message.RECEIVE);
        userMessageMapper.insertWithMsgId(receiverId, senderId, msgId, message);
        if (sync) {
            messageCache.saveMessage(message, receiverId, senderId);
        }
        return msgId;
    }

    /**
     * 从redis同步, 如果还是漏了就让客户端主动从数据库同步{@link ChatServiceImpl#syncOfflineMessage(int, int)}
     * <p>
     * 每次最多同步100条
     */
    @Override
    public List<Message> syncOnlineMessage(int uid, int start, int end) {
        final int limit = 100;
        int len = Math.min(limit, end - start);

        ArrayList<String> ids = new ArrayList<>(len);
        for (int i = end - 1; i >= end - len; i--) {
            ids.add(String.valueOf(i));
        }

        return messageCache.queryCachedMessage(uid, ids);
    }

    /**
     * 从数据库同步, 每次最多同步100条，多的分批次同步即可
     */
    @Override
    public List<Message> syncOfflineMessage(int uid, int start) {
        final int limit = 100;
        return userMessageMapper.selectRange(uid, start, limit);
    }

    @Override
    public int queryMaxMessageId(int uid) {
        return 0;
    }


}
