package pers.xds.wtuapp.web.service;

import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;
import pers.xds.wtuapp.web.database.view.CommunityMessagePost;
import pers.xds.wtuapp.web.database.view.CommunityTipQueryType;
import pers.xds.wtuapp.web.service.bean.PostReply;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-12-10 13:57
 */
public interface CommunityService {

    /**
     * 向社区发送新消息
     * @param author 作者id
     * @param communityMessage 消息内容, 将{@link CommunityMessage#pid}置为0，表示发布新的消息，反之则为回复某个消息
     * @param enableNotification 开启通知，一般用于回复功能
     * @return 新增消息的id<p>
     *     - {@link ServiceCode#RATE_LIMIT}: 表示用户到达了消息发送上限
     */
    ServiceCodeWrapper<Integer> postMessage(int author, CommunityMessage communityMessage, boolean enableNotification);

    /**
     * 删除社区消息(不能删除回复)
     * @param author 作者id
     * @param messageId 消息id
     */
    void deleteMessage(int author, int messageId);


    /**
     * 查询最新的社区消息
     *
     * @param maxId 最大的消息id
     * @return 最新的社区消息
     */
    List<CommunityMessagePost> queryNewlyCommunityMessage(@Nullable Integer maxId);

    /**
     * 查询最新的社区消息，但是其id大于minId
     *
     * @param minId 最小消息id(不包括)
     * @return 最新的社区信息
     */
    List<CommunityMessagePost> queryNewlyCommunityMessageByMinId(int minId);

    /**
     * 查询某条消息的评论(不管是一级评论还是二级评论，都可以查询)
     *
     * @param pid  根消息id
     * @param page 第几页
     * @param size 每页显示多大
     * @return 评论
     */
    List<CommunityMessagePost> queryMessageReply(int pid, int page, int size);

    /**
     * 查询一级评论下的部分二级评论信息。
     *
     * @param pids 要查询的一级评论id
     * @return 相关二级评论
     */
    List<CommunityMessagePost> querySubReplyPreview(List<Integer> pids);

    /**
     * 点赞/踩 某条消息
     * @param uid 用户id
     * @param messageId 消息id
     * @param thumbsUp 是否点赞，false为踩, null为中立
     * @return 服务状态码<p>
     *     - {@link ServiceCode#RATE_LIMIT} 用户点赞过于频繁
     *     - {@link ServiceCode#NOT_AVAILABLE} 用户已经点赞过了
     */
    ServiceCode feedbackMessage(int uid, int messageId, Boolean thumbsUp);

    /**
     * 查询消息提醒
     * @param uid 用户id
     * @return 消息提醒
     */
    List<CommunityTipQueryType> queryMessageTip(int uid);

    /**
     * 根据消息id查询消息
     * @param messageId 消息id
     * @return 消息信息
     */
    @Nullable
    CommunityMessagePost queryMessageById(int messageId);

    /**
     * 查询帖子的评论及二级回复预览
     * @param messageId 消息id
     * @param page 第几页，从1开始
     * @param size 每页大小
     * @return 评论及二级回复
     */
    @Nullable
    PostReply queryPostReply(int messageId, int page, int size);

}
