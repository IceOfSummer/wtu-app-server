package pers.xds.wtuapp.im.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.im.database.MessageReceiveMapper;
import pers.xds.wtuapp.im.database.UserMessageMapper;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.database.bean.MessageReceive;
import pers.xds.wtuapp.im.redis.MessageCache;
import pers.xds.wtuapp.im.service.ChatService;

import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2022-09-03 16:41
 */
@Service
public class ChatServiceImpl implements ChatService {

    private UserMessageMapper userMessageMapper;

    private MessageCache messageCache;

    private MessageReceiveMapper messageReceiveMapper;

    @Autowired
    public void setMessageReceiveMapper(MessageReceiveMapper messageReceiveMapper) {
        this.messageReceiveMapper = messageReceiveMapper;
    }

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
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Message saveMessage(String content, int sender, int to, boolean sync) throws NoSuchElementException {
        // 理论上应该要检查用户是否存在，但实现起来有点麻烦
        Integer receiveId = messageReceiveMapper.selectReceivedId(to);
        int msgId;
        if (receiveId == null) {
            msgId = 0;
            messageReceiveMapper.insert(new MessageReceive(to));
        } else {
            msgId = receiveId;
            messageReceiveMapper.increaseReceivedId(to);
        }
        Message message = new Message(to, msgId, sender, content);
        userMessageMapper.insert(message);
        if (sync) {
            messageCache.saveMessage(message);
        }
        return message;
    }

}
