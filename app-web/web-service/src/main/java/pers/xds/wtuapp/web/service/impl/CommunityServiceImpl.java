package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;
import pers.xds.wtuapp.web.database.bean.FeedbackRecord;
import pers.xds.wtuapp.web.database.mapper.CommunityMessageMapper;
import pers.xds.wtuapp.web.database.mapper.FeedbackRecordMapper;
import pers.xds.wtuapp.web.redis.CounterCache;
import pers.xds.wtuapp.web.redis.common.Duration;
import pers.xds.wtuapp.web.service.CommunityService;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.ServiceCodeWrapper;

import java.util.List;
import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2022-12-10 14:11
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    private CommunityMessageMapper communityMessageMapper;

    private CounterCache counterCache;

    private FeedbackRecordMapper feedbackRecordMapper;

    @Autowired
    public void setFeedbackRecordMapper(FeedbackRecordMapper feedbackRecordMapper) {
        this.feedbackRecordMapper = feedbackRecordMapper;
    }

    @Autowired
    public void setCounterCache(CounterCache counterCache) {
        this.counterCache = counterCache;
    }

    @Autowired
    public void setCommunityMessageMapper(CommunityMessageMapper communityMessageMapper) {
        this.communityMessageMapper = communityMessageMapper;
    }

    public static final String POST_MESSAGE_KEY_PREFIX = "CommunityService#PostMessage:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCodeWrapper<Integer> postMessage(int author, CommunityMessage communityMessage) {
        String key = POST_MESSAGE_KEY_PREFIX + author;
        int invokeCount = counterCache.getInvokeCount(key);
        final int maxAllowCount = 100;
        if (invokeCount > maxAllowCount) {
            return ServiceCodeWrapper.fail(ServiceCode.RATE_LIMIT);
        }
        Integer pid = communityMessage.getPid();
        if (pid != 0) {
            // reply_count 加一
            communityMessageMapper.increaseReplyCount(pid);
        }
        communityMessage.setAuthor(author);
        communityMessageMapper.insert(communityMessage);
        counterCache.increaseInvokeCount(invokeCount, key);
        return ServiceCodeWrapper.success(communityMessage.getId());
    }

    @Override
    public void deleteMessage(int author, int messageId) {
        // 删除根消息
        communityMessageMapper.deleteCommunityMessage(author, messageId);
        // 删除子消息
        // FIXME: 这里若删除的是整个文章，则二级回复是不会被删除的，而且即时删除比较耗时，应该添加到一个任务队列
        communityMessageMapper.deleteCommunityMessageByPid(messageId);
    }

    @Override
    public List<Map<String, String>> queryNewlyCommunityMessage(Integer maxId) {
        final int size = 4;
        return communityMessageMapper.selectMessageByMaxId(maxId, size);
    }

    @Override
    public List<Map<String, String>> queryNewlyCommunityMessageByMinId(int minId) {
        final int size = 4;
        return communityMessageMapper.selectMessageByMinId(minId, size);
    }

    @Override
    public List<Map<String, String>> queryMessageReply(int pid, int page, int size) {
        return communityMessageMapper.selectMessageByPid(pid, new Page<>(page, size)).getRecords();
    }

    @Override
    public List<Map<String, String>> querySubReplyPreview(List<Integer> pids) {
        final int size = 3;
        final int maxAllowQuery = 8;
        if (pids.size() > maxAllowQuery) {
            throw new IllegalArgumentException();
        }
        return communityMessageMapper.selectMessageReplyPreview(pids, size);
    }

    private static final String FEED_BACK_KEY_PREFIX = "CommunityService:feedbackMessage:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCode feedbackMessage(int uid, int messageId, Boolean thumbsUp) {
        final int maxAllowInvoke = 80;
        String key = FEED_BACK_KEY_PREFIX + uid;
        int invokeCount = counterCache.getInvokeCount(key, Duration.HOUR);
        if (invokeCount > maxAllowInvoke) {
            return ServiceCode.RATE_LIMIT;
        }
        FeedbackRecord feedbackRecord = feedbackRecordMapper.selectFeedbackRecord(uid, messageId);
        int up, down;
        if (feedbackRecord != null && feedbackRecord.getLike() != null) {
            boolean oldAttitude = feedbackRecord.getLike();
            if (thumbsUp == null) {
                // 当前状态为不点赞也不点踩, 取消之前的点赞/踩
                if (oldAttitude) {
                    up = -1;
                    down = 0;
                } else {
                    up = 0;
                    down = -1;
                }
            } else {
                boolean attitude = thumbsUp;
                if (attitude == oldAttitude) {
                    // 当前态度和之前一样
                    return ServiceCode.NOT_AVAILABLE;
                } else if (attitude) {
                    // 当前为点赞，之前为点踩
                    up = 1;
                    down = -1;
                } else {
                    // 当前为点踩，之前为点赞
                    up = -1;
                    down = 1;
                }
            }
        } else {
            if (thumbsUp == null) {
                // 没有记录，也不表态
                return ServiceCode.SUCCESS;
            } else if (thumbsUp) {
                up = 1;
                down = 0;
            } else {
                up = 0;
                down = 1;
            }
        }
        FeedbackRecord fb = new FeedbackRecord(uid, messageId, thumbsUp);
        if (feedbackRecord == null) {
            feedbackRecordMapper.insert(fb);
        } else {
            feedbackRecordMapper.updateAttitude(fb);
        }
        communityMessageMapper.modifyFeedbackAbsolutely(messageId, up, down);
        counterCache.increaseInvokeCountAsync(invokeCount, key);
        return ServiceCode.SUCCESS;
    }


}
