package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.database.bean.Message;
import pers.xds.wtuapp.web.database.bean.MessageReceive;
import pers.xds.wtuapp.web.database.mapper.MessageMapper;
import pers.xds.wtuapp.web.database.mapper.MessageReceiveMapper;
import pers.xds.wtuapp.web.service.MessageService;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-10-31 15:19
 */
@Service
public class MessageServiceImpl implements MessageService {

    private MessageMapper messageMapper;

    private MessageReceiveMapper messageReceiveMapper;

    @Autowired
    public void setMessageReceiveMapper(MessageReceiveMapper messageReceiveMapper) {
        this.messageReceiveMapper = messageReceiveMapper;
    }

    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Override
    public List<Message> getOfflineMessage(int uid, int receivedId) {
        return messageMapper.queryOfflineMessage(uid, receivedId);
    }

    @Override
    public void markMessageReceived(int uid, int receivedId) {
        messageReceiveMapper.updateUserReceivedMsgId(uid, receivedId);
    }

    @Override
    public int getMessageReceivedId(int uid) {
        MessageReceive messageReceive = messageReceiveMapper.selectById(uid);
        if (messageReceive == null) {
            messageReceiveMapper.insert(new MessageReceive(uid));
            return 0;
        }
        return messageReceive.getReceiveId();
    }
}
