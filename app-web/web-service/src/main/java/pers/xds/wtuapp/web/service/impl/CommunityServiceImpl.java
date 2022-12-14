package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;
import pers.xds.wtuapp.web.database.bean.CommunityTip;
import pers.xds.wtuapp.web.database.bean.FeedbackRecord;
import pers.xds.wtuapp.web.database.mapper.CommunityMessageMapper;
import pers.xds.wtuapp.web.database.mapper.CommunityTipMapper;
import pers.xds.wtuapp.web.database.mapper.FeedbackRecordMapper;
import pers.xds.wtuapp.web.database.view.CommunityMessagePost;
import pers.xds.wtuapp.web.database.view.CommunityMessageReply;
import pers.xds.wtuapp.web.database.view.CommunityTipQueryType;
import pers.xds.wtuapp.web.redis.CounterCache;
import pers.xds.wtuapp.web.redis.common.Duration;
import pers.xds.wtuapp.web.service.CommunityService;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.ServiceCodeWrapper;
import pers.xds.wtuapp.web.service.bean.PostReply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-12-10 14:11
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    private CommunityMessageMapper communityMessageMapper;

    private CounterCache counterCache;

    private FeedbackRecordMapper feedbackRecordMapper;

    private CommunityTipMapper communityTipMapper;

    @Autowired
    public void setCommunityTipMapper(CommunityTipMapper communityTipMapper) {
        this.communityTipMapper = communityTipMapper;
    }


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
    public ServiceCodeWrapper<Integer> postMessage(int author, CommunityMessage communityMessage, boolean enableNotification) {
        String key = POST_MESSAGE_KEY_PREFIX + author;
        int invokeCount = counterCache.getInvokeCount(key);
        final int maxAllowCount = 100;
        if (invokeCount > maxAllowCount) {
            return ServiceCodeWrapper.fail(ServiceCode.RATE_LIMIT);
        }
        Integer pid = communityMessage.getPid();
        if (pid != 0) {
            // reply_count ??????
            communityMessageMapper.increaseReplyCount(pid);
            // ?????????????????????
            CommunityMessage parentMessage = communityMessageMapper.selectSimplyById(pid);
            if (enableNotification) {
                final int maxLen = 11;
                String content = communityMessage.getContent();
                String contentPreview;
                if (content.length() > maxLen) {
                    contentPreview = content.substring(0, maxLen) + "...";
                } else {
                    contentPreview = content;
                }
                communityTipMapper.insertOrUpdate(new CommunityTip(
                        pid,
                        parentMessage.getTitle(),
                        parentMessage.getAuthor(),
                        author,
                        parentMessage.getPid() == 0 ? CommunityTip.TYPE_POST_REPLY : CommunityTip.TYPE_COMMENT_REPLY,
                        contentPreview
                ));
            }
        }
        communityMessage.setAuthor(author);
        communityMessageMapper.insert(communityMessage);
        counterCache.increaseInvokeCount(key);
        return ServiceCodeWrapper.success(communityMessage.getId());
    }

    @Override
    public void deleteMessage(int author, int messageId) {
        // ???????????????
        communityMessageMapper.deleteCommunityMessage(author, messageId);
        // ???????????????
        // FIXME: ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        communityMessageMapper.deleteCommunityMessageByPid(messageId);
    }

    @Override
    public List<CommunityMessagePost> queryNewlyCommunityMessage(Integer maxId) {
        final int size = 4;
        return communityMessageMapper.selectMessageByMaxId(maxId, size);
    }

    @Override
    public List<CommunityMessagePost> queryNewlyCommunityMessageByMinId(int minId) {
        final int size = 4;
        return communityMessageMapper.selectMessageByMinId(minId, size);
    }

    @Override
    public List<CommunityMessagePost> queryMessageReply(int pid, int page, int size) {
        return communityMessageMapper.selectMessageByPid(pid, new Page<>(page, size)).getRecords();
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
                // ????????????????????????????????????, ?????????????????????/???
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
                    // ???????????????????????????
                    return ServiceCode.NOT_AVAILABLE;
                } else if (attitude) {
                    // ?????????????????????????????????
                    up = 1;
                    down = -1;
                } else {
                    // ?????????????????????????????????
                    up = -1;
                    down = 1;
                }
            }
        } else {
            if (thumbsUp == null) {
                // ???????????????????????????
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
        counterCache.increaseInvokeCount(key);
        return ServiceCode.SUCCESS;
    }

    @Override
    public List<CommunityTipQueryType> queryMessageTip(int uid) {
        List<CommunityTipQueryType> tips = communityTipMapper.selectTips(uid);
        if (!tips.isEmpty()) {
            communityTipMapper.clearTip(uid);
        }
        return tips;
    }


    @Override
    public CommunityMessagePost queryMessageById(int messageId) {
        return communityMessageMapper.selectMessageById(messageId);
    }

    @Override
    public PostReply queryPostReply(int messageId, int page, int size) {
        Page<CommunityMessageReply> pg = new Page<>(page, size);
        List<CommunityMessagePost> records = communityMessageMapper.selectMessageByPid(messageId, pg).getRecords();
        if (records.isEmpty()) {
            return PostReply.EMPTY_POST_REPLY;
        }
        PostReply postReply = new PostReply();
        postReply.setReply(records);

        ArrayList<Integer> pids = new ArrayList<>(records.size());
        for (CommunityMessagePost record : records) {
            if (record.getReplyCount() > 0) {
                pids.add(record.getId());
            }
        }
        List<CommunityMessagePost> preview;
        if (pids.isEmpty()) {
            preview = Collections.emptyList();
        } else {
            preview = communityMessageMapper.selectMessageReplyPreview(pids, 4);
        }
        postReply.setSubReply(preview);
        return postReply;
    }


}
