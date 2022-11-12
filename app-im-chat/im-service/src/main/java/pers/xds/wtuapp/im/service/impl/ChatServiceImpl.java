package pers.xds.wtuapp.im.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.im.database.MessageReceiveMapper;
import pers.xds.wtuapp.im.database.UserMessageMapper;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.database.bean.MessageReceive;
import pers.xds.wtuapp.im.redis.MessageCache;
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
    public Pair<Integer, Integer> saveMessage(Message message, boolean sync) throws NoSuchElementException {
        int sender = message.getTo();
        int receiver = message.getUid();
        // 理论上应该要检查用户是否存在，但由于是俩数据库，实现起来有点麻烦
        Pair<Integer, Integer> pair = ensureBeanExist(messageReceiveMapper.selectTwo(sender, receiver), sender, receiver);
        int senderId = pair.getFirst();
        int receiverId = pair.getSecond();
        userMessageMapper.insertSelfAndTargetMessage(message, receiverId, senderId);
        if (sync) {
            messageCache.saveMessage(message);
        }
        return pair;
    }

    /**
     * 确保对应的实体存在并且返回对应的接收id
     * <p>
     * <b>在返回前会自动为其字段加一</b>
     * @param receives 查询结果
     * @param sender 发送者
     * @param receiver 接收者
     * @return 一个二元组，第一个为发送者的消息id，第二个为接受者的消息id
     */
    private Pair<Integer, Integer> ensureBeanExist(List<MessageReceive> receives, int sender, int receiver) {
        final int expect = 2;
        if (receives.size() == expect) {
            messageReceiveMapper.increaseReceivedIdTwo(sender, receiver);
            return Pair.of(receives.get(0).getReceivedId(), receives.get(1).getReceivedId());
        }
        if (receives.size() == 1) {
            // 插入一个
            int target = sender;
            if (receives.get(0).getUid() != target) {
                target = receiver;
            }
            messageReceiveMapper.insert(new MessageReceive(target));
            if (target == sender) {
                messageReceiveMapper.increaseReceivedId(receiver);
                return Pair.of(0, receives.get(0).getReceivedId());
            } else {
                messageReceiveMapper.increaseReceivedId(sender);
                return Pair.of(receives.get(0).getReceivedId(), 0);
            }
        }
        // 插入俩
        messageReceiveMapper.insertTwo(sender, receiver);
        return Pair.of(0, 0);
    }

}
