package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;
import pers.xds.wtuapp.web.database.bean.EventRemind;
import pers.xds.wtuapp.web.database.common.EventRemindType;
import pers.xds.wtuapp.web.database.mapper.CommunityMessageMapper;
import pers.xds.wtuapp.web.database.mapper.EventRemindMapper;
import pers.xds.wtuapp.web.database.mapper.UserMapper;
import pers.xds.wtuapp.web.service.EventRemindService;
import pers.xds.wtuapp.web.service.bean.CombinedEventRemind;

import java.util.*;

/**
 * @author DeSen Xu
 * @date 2023-01-14 14:01
 */
@Service
public class EventRemindServiceImpl implements EventRemindService {

    private EventRemindMapper eventRemindMapper;

    private CommunityMessageMapper communityMessageMapper;

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setCommunityMessageMapper(CommunityMessageMapper communityMessageMapper) {
        this.communityMessageMapper = communityMessageMapper;
    }

    @Autowired
    public void setEventRemindMapper(EventRemindMapper eventRemindMapper) {
        this.eventRemindMapper = eventRemindMapper;
    }

    @Override
    public List<CombinedEventRemind> queryUnreadRemind(int uid) {
        List<EventRemind> forCombine = eventRemindMapper.selectRemindsForCombine(uid);
        Map<Pair<Integer, Integer>, List<EventRemind>> combineEventReminds = splitEventReminds(forCombine);

        ArrayList<CombinedEventRemind> result = new ArrayList<>(Math.min(forCombine.size(), 100));

        // 当同类型消息超过这个数量就直接聚合为一条
        final int maxCombineSize = 6;
        combineEventReminds.forEach((pair, eventReminds) -> {
            if (eventReminds.size() >= maxCombineSize) {
                // 聚合为一条
                Integer uid1 = eventReminds.get(0).getSenderId();
                Integer uid2 = eventReminds.get(1).getSenderId();
                String name1 = userMapper.selectNicknameOnly(uid1);
                String name2 = userMapper.selectNicknameOnly(uid2);
                CombinedEventRemind combinedEventRemind = toCombinedEventRemind(eventReminds.get(0));
                combinedEventRemind.setCount(eventReminds.size());
                combinedEventRemind.setSenderIds(new int[]{uid1, uid2});
                processingCombinedMessage(combinedEventRemind, name1, name2);
                result.add(combinedEventRemind);
            } else {
                // 单独发送
                for (EventRemind eventRemind : eventReminds) {
                    CombinedEventRemind combinedEventRemind = toCombinedEventRemind(eventRemind);
                    combinedEventRemind.setSenderIds(new int[]{eventRemind.getSenderId()});
                    processingCombinedMessage(combinedEventRemind, userMapper.selectNicknameOnly(eventRemind.getSenderId()));
                    result.add(combinedEventRemind);
                }
            }
        });
        return result;
    }

    /**
     * 将{@link EventRemind}转换为{@link CombinedEventRemind}
     * <p>
     * 需要自己手动设置{@link CombinedEventRemind#senderIds}属性！
     */
    private CombinedEventRemind toCombinedEventRemind(EventRemind eventRemind) {
        CombinedEventRemind combinedEventRemind = new CombinedEventRemind();
        combinedEventRemind.setId(eventRemind.getEventRemindId());
        combinedEventRemind.setTargetContent(eventRemind.getTargetContent());
        combinedEventRemind.setSourceType(eventRemind.getSourceType());
        combinedEventRemind.setSourceId(eventRemind.getSourceId());
        combinedEventRemind.setSourceContent(eventRemind.getSourceContent());
        combinedEventRemind.setCreateTime(eventRemind.getCreateTime());
        return combinedEventRemind;
    }

    /**
     * 进一步处理事件提醒对象
     * @param combinedEventRemind 事件提醒对象
     * @param nicknames 该事件涉及的用户昵称
     */
    private void processingCombinedMessage(CombinedEventRemind combinedEventRemind, String...nicknames) {
        StringBuilder title = new StringBuilder(nicknames.length * 20);
        if (nicknames.length == 1) {
            title.append(nicknames[0]);
        } else {
            for (String name : nicknames) {
                title.append(name).append(',');
            }
            title.deleteCharAt(title.length() - 1);
            title.append("等")
                    .append(combinedEventRemind.getCount())
                    .append("人");
        }
        switch (combinedEventRemind.getSourceType()) {
            case EventRemindType.LIKE_COMMENT:
                title.append("点赞了你的评论");
                break;
            case EventRemindType.LIKE_POST:
                title.append("点赞了你的帖子");
                break;
            case EventRemindType.REPLY_POST:
                title.append("回复了你的帖子");
                break;
            case EventRemindType.REPLY_SUB:
                title.append("回复了你的评论");
                break;
            default:
                throw new IllegalArgumentException("未知的消息类型: " + combinedEventRemind.getSourceType());
        }
        // do processing
        combinedEventRemind.setRemindTitle(title.toString());
    }

    /**
     * 以sourceIdd和sourceType进行分组
     * @param eventReminds 消息提醒
     * @return 分组后的消息提醒. Pair左: sourceId, Pair右: sourceType
     */
    private Map<Pair<Integer, Integer>, List<EventRemind>> splitEventReminds(List<EventRemind> eventReminds) {
        Map<Pair<Integer, Integer>, List<EventRemind>> map = new HashMap<>();
        final int possibleSize = eventReminds.size() / 3;
        for (EventRemind eventRemind : eventReminds) {
            map.compute(Pair.of(eventRemind.getSourceId(), eventRemind.getSourceType()), (integer, reminds) -> {
                if (reminds == null) {
                    reminds = new ArrayList<>(possibleSize);
                }
                reminds.add(eventRemind);
                return reminds;
            });
        }
        return map;
    }

    @Override
    public Integer queryUnreadMessageCount(int uid) {
        return eventRemindMapper.selectUnreadMessageCount(uid);
    }

    @Override
    public void markAllRead(int uid) {
        eventRemindMapper.markAllRead(uid);
    }

    @Async
    @Override
    public void insertLikeEventTip(int sender, int source) {
        EventRemind eventRemind = new EventRemind();
        CommunityMessage communityMessage = communityMessageMapper.selectSimplyWithContentById(source);
        eventRemind.setReceiverId(communityMessage.getAuthor());
        eventRemind.setSenderId(sender);
        eventRemind.setSourceId(source);
        if (communityMessage.getPid() == 0) {
            eventRemind.setTargetContent(communityMessage.getTitle());
            eventRemind.setSourceType(EventRemindType.LIKE_POST);
        } else {
            eventRemind.setTargetContent(communityMessage.getContent());
            eventRemind.setSourceType(EventRemindType.LIKE_COMMENT);
        }
        eventRemindMapper.insert(eventRemind);
    }

    @Async
    @Override
    public void insertReplyEventTip(int sender, int source, int pid, String content) {
        final int maxContentSize = 10;
        EventRemind eventRemind = new EventRemind();
        CommunityMessage communityMessage = communityMessageMapper.selectSimplyWithContentById(pid);
        eventRemind.setReceiverId(communityMessage.getAuthor());
        eventRemind.setSenderId(sender);
        eventRemind.setSourceId(source);
        eventRemind.setSourceContent(content.length() > maxContentSize ? content.substring(0, maxContentSize) : content);
        if (communityMessage.getPid() == 0) {
            eventRemind.setTargetContent(communityMessage.getTitle());
            eventRemind.setSourceType(EventRemindType.REPLY_POST);
        } else {
            eventRemind.setTargetContent(communityMessage.getContent());
            eventRemind.setSourceType(EventRemindType.REPLY_SUB);
        }
        eventRemindMapper.insert(eventRemind);
    }

}
