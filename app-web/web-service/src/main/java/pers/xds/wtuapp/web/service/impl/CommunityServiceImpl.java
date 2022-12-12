package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;
import pers.xds.wtuapp.web.database.mapper.CommunityMessageMapper;
import pers.xds.wtuapp.web.redis.CounterCache;
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
        final int size = 5;
        return communityMessageMapper.selectMessageByMaxId(maxId, size);
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

    @Override
    public void feedbackMessage(int messageId, boolean isArticle, boolean thumbsUp) {
        // TODO
    }


}
