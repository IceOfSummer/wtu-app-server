package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;
import pers.xds.wtuapp.web.database.bean.FeedbackRecord;
import pers.xds.wtuapp.web.database.mapper.CommunityMessageMapper;
import pers.xds.wtuapp.web.database.mapper.FeedbackRecordMapper;
import pers.xds.wtuapp.web.database.view.CommunityMessagePost;
import pers.xds.wtuapp.web.database.view.CommunityMessageReply;
import pers.xds.wtuapp.web.service.*;
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

    private FeedbackRecordMapper feedbackRecordMapper;


    private EventRemindService eventRemindService;

    @Autowired
    public void setEventRemindService(EventRemindService eventRemindService) {
        this.eventRemindService = eventRemindService;
    }

    @Autowired
    public void setFeedbackRecordMapper(FeedbackRecordMapper feedbackRecordMapper) {
        this.feedbackRecordMapper = feedbackRecordMapper;
    }

    @Autowired
    public void setCommunityMessageMapper(CommunityMessageMapper communityMessageMapper) {
        this.communityMessageMapper = communityMessageMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCodeWrapper<Integer> postMessage(int author, CommunityMessage communityMessage, boolean enableNotification) {
        Integer pid = communityMessage.getPid();
        communityMessage.setAuthor(author);
        communityMessageMapper.insert(communityMessage);
        if (pid != 0) {
            // reply_count 加一
            communityMessageMapper.increaseReplyCount(pid);
            // 提醒父消息用户
            if (enableNotification) {
                eventRemindService.insertReplyEventTip(author, communityMessage.getId(), communityMessage.getPid(), communityMessage.getContent());
            }
        }
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
        return communityMessageMapper.selectMessageByPid(pid, new Page<>(page, size, false)).getRecords();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCode feedbackMessage(int uid, int messageId, Boolean thumbsUp) {
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
        if (up == 1) {
            eventRemindService.insertLikeEventTip(uid, messageId);
        }
        return ServiceCode.SUCCESS;
    }

    @Override
    public CommunityMessagePost queryMessageById(int messageId) {
        return communityMessageMapper.selectMessageById(messageId);
    }

    @Override
    public PostReply queryPostReply(int messageId, int page, int size) {
        Page<CommunityMessageReply> pg = new Page<>(page, size, false);
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
