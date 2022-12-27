package pers.xds.wtuapp.web.database.bean;

import java.sql.Timestamp;

/**
 * @author DeSen Xu
 * @date 2022-12-24 20:35
 */
public class CommunityTip {


    /**
     * 消息id
     */
    private Integer messageId;

    /**
     * 消息标题
     */
    private String messageTitle;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 上次回复的id
     */
    private Integer lastReplyUid;

    /**
     * 回复数量
     */
    private Integer count;

    /**
     * 上次回复时间
     */
    private Timestamp lastReplyTime;

    /**
     * 最后回复内容
     */
    private String lastReplyContent;

    /**
     * 当发送的帖子被回复时
     */
    public static final Integer TYPE_POST_REPLY = 0;

    /**
     * 当发送的评论被回复时
     */
    public static final Integer TYPE_COMMENT_REPLY = 1;

    /**
     * 消息类型
     */
    private Integer type;

    public CommunityTip() {
    }

    public CommunityTip(Integer messageId, String messageTitle, Integer uid, Integer lastReplyUid, Integer type) {
        this(messageId, messageTitle, uid, lastReplyUid, type, null);
    }

    public CommunityTip(Integer messageId, String messageTitle, Integer uid, Integer lastReplyUid, Integer type, String lastReplyContent) {
        this.messageId = messageId;
        this.messageTitle = messageTitle;
        this.uid = uid;
        this.lastReplyUid = lastReplyUid;
        this.type = type;
        this.lastReplyContent = lastReplyContent;
    }



    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getLastReplyUid() {
        return lastReplyUid;
    }

    public void setLastReplyUid(Integer lastReplyUid) {
        this.lastReplyUid = lastReplyUid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Timestamp getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Timestamp lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLastReplyContent() {
        return lastReplyContent;
    }

    public void setLastReplyContent(String lastReplyContent) {
        this.lastReplyContent = lastReplyContent;
    }
}
