package pers.xds.wtuapp.web.service;

import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;

import java.util.List;
import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2022-12-10 13:57
 */
public interface CommunityService {

    /**
     * 向社区发送新消息
     * @param author 作者id
     * @param communityMessage 消息内容, 将{@link CommunityMessage#pid}置为0，表示发布新的消息，反之则为回复某个消息
     * @return 新增消息的id<p>
     *     - {@link ServiceCode#RATE_LIMIT}: 表示用户到达了消息发送上限
     */
    ServiceCodeWrapper<Integer> postMessage(int author, CommunityMessage communityMessage);

    /**
     * 删除社区消息(不能删除回复)
     * @param author 作者id
     * @param messageId 消息id
     */
    void deleteMessage(int author, int messageId);


    /**
     * 查询最新的社区消息
     * @param maxId 最大的消息id
     * @return 最新的社区消息
     */
    List<Map<String, String>> queryNewlyCommunityMessage(@Nullable Integer maxId);

    /**
     * 查询某条消息的评论(不管是一级评论还是二级评论，都可以查询)
     * @param pid 根消息id
     * @param page 第几页
     * @param size 每页显示多大
     * @return 评论
     */
    List<Map<String, String>> queryMessageReply(int pid, int page, int size);

    /**
     * 查询一级评论下的部分二级评论信息。
     * @param pids 要查询的一级评论id
     * @return 相关二级评论
     */
    List<Map<String, String>> querySubReplyPreview(List<Integer> pids);

    /**
     * 点赞/踩 某条消息
     * @param messageId 消息id
     * @param isArticle 是否为文章(即对文章的整个内容点赞，而非对某个评论点赞)
     * @param thumbsUp 是否点赞，false为踩
     */
    void feedbackMessage(int messageId, boolean isArticle, boolean thumbsUp);

}