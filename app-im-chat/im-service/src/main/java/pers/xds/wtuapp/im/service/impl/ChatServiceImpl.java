package pers.xds.wtuapp.im.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.im.database.MessageReceiveMapper;
import pers.xds.wtuapp.im.database.UserMessageMapper;
import pers.xds.wtuapp.im.database.bean.MessageReceive;
import pers.xds.wtuapp.im.database.bean.UserMessage;
import pers.xds.wtuapp.im.service.ChatService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2022-09-03 16:41
 */
@Service
public class ChatServiceImpl implements ChatService {

    private UserMessageMapper userMessageMapper;

    private MessageReceiveMapper messageReceiveMapper;

    @Autowired
    public void setMessageReceiveMapper(MessageReceiveMapper messageReceiveMapper) {
        this.messageReceiveMapper = messageReceiveMapper;
    }

    @Autowired
    public void setUserMessageMapper(UserMessageMapper userMessageMapper) {
        this.userMessageMapper = userMessageMapper;
    }

    @Override
    public List<UserMessage> getUnreceivedMessage(int userid) {
        return userMessageMapper.queryUnreceivedMessage(userid);
    }

    @Override
    public void messageReceiveAck(int userId, int receivedId) {
        Integer status = messageReceiveMapper.updateUserReceivedMsgId(userId, receivedId);
        if (status == 0) {
            // 失败
            throw new IllegalArgumentException("receivedId greater than unreceivedId");
        }
    }

    @Override
    @NonNull
    public MessageReceive getMessageReceiveStatus(int userid) {
        MessageReceive messageReceive = messageReceiveMapper.selectById(userid);
        if (messageReceive == null) {
            messageReceive = new MessageReceive(userid);
            messageReceiveMapper.insert(messageReceive);
        }
        return messageReceive;
    }


    /**
     * 发送离线消息
     * @param message 消息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOfflineMessage(UserMessage message) throws NoSuchElementException {
        MessageReceive messageReceive = messageReceiveMapper.selectById(message.getMsgTo());
        if (messageReceive == null) {
            throw new NoSuchElementException();
        }
        int nextId = messageReceive.getUnreceivedId();
        messageReceiveMapper.increaseMessageCounter(message.getMsgTo());
        userMessageMapper.insert(message);
    }
}
