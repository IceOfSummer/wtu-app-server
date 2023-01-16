package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.service.bean.CombinedEventRemind;

import java.util.List;

/**
 * 事件提醒服务
 * @author DeSen Xu
 * @date 2023-01-14 13:58
 */
public interface EventRemindService {


    /**
     * 查询未读提醒
     * @param uid 用户id
     * @return 未读提醒
     */
    List<CombinedEventRemind> queryUnreadRemind(int uid);

    /**
     * 查询未读消息数量
     * @param uid 用户id
     * @return 未读消息数量。若大于100，则会直接返回100
     */
    Integer queryUnreadMessageCount(int uid);

    /**
     * 标记所有消息都已读
     * @param uid 用户id
     */
    void markAllRead(int uid);

    /**
     * 插入一条广场帖子点赞提醒.
     * @param sender 发送者
     * @param source 来源(帖子/回复id)
     */
    void insertLikeEventTip(int sender, int source);

    /**
     * 插入一条评论提醒
     * @param sender 发送者
     * @param source 来源(帖子/回复id)
     * @param pid 父消息id
     * @param content 评论内容
     */
    void insertReplyEventTip(int sender, int source, int pid, String content);
}
