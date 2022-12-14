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

import java.util.ArrayList;
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
            messageCache.saveMessage(message, receiverId, senderId);
        }
        return pair;
    }

    /**
     * 从redis同步, 如果还是漏了就让客户端主动从数据库同步{@link ChatServiceImpl#syncOfflineMessage(int, int, int)}
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
     * 从数据库同步, 每次最多同步200条，多的分批次同步即可
     */
    @Override
    public List<Message> syncOfflineMessage(int uid, int start, int end) {
        final int limit = 100;
        return userMessageMapper.selectRange(uid, end - Math.min(limit, end - start), end);
    }

    @Override
    public int queryMaxMessageId(int uid) {
        Integer id = messageReceiveMapper.selectReceivedId(uid);
        if (id == null) {
            // insert
            messageReceiveMapper.insert(new MessageReceive(uid));
            id = 0;
        }
        return id;
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
            if (receives.get(0).getUid() == target) {
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
