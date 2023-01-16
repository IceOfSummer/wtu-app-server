package pers.xds.wtuapp.web.database.bean;

import java.sql.Timestamp;

/**
 * 事件提醒
 * @TableName event_remind
 */
public class EventRemind {

    /**
     * 消息id
     */
    private Integer eventRemindId;

    /**
     * 接收者id
     */
    private Integer receiverId;

    /**
     * 事件源;如评论ID、文章ID
     */
    private Integer sourceId;

    /**
     * 事件源类型
     * @see pers.xds.wtuapp.web.database.common.EventRemindType
     */
    private Integer sourceType;

    /**
     * 事件源内容
     */
    private String sourceContent;

    /**
     * 是否已读
     */
    private Integer state;

    /**
     * 操作者id
     */
    private Integer senderId;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 目标的内容;若对文章或评论点赞则在这里提供文章或评论的标题或内容
     */
    private String targetContent;

    /**
     * 消息id
     */
    public Integer getEventRemindId() {
        return eventRemindId;
    }

    /**
     * 消息id
     */
    public void setEventRemindId(Integer eventRemindId) {
        this.eventRemindId = eventRemindId;
    }

    /**
     * 接收者id
     */
    public Integer getReceiverId() {
        return receiverId;
    }

    /**
     * 接收者id
     */
    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * 事件源;如评论ID、文章ID
     */
    public Integer getSourceId() {
        return sourceId;
    }

    /**
     * 事件源;如评论ID、文章ID
     */
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 事件源类型
     */
    public Integer getSourceType() {
        return sourceType;
    }

    /**
     * 事件源类型
     */
    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 事件源内容
     */
    public String getSourceContent() {
        return sourceContent;
    }

    /**
     * 事件源内容
     */
    public void setSourceContent(String sourceContent) {
        this.sourceContent = sourceContent;
    }

    /**
     * 是否已读
     */
    public Integer getState() {
        return state;
    }

    /**
     * 是否已读
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 操作者id
     */
    public Integer getSenderId() {
        return senderId;
    }

    /**
     * 操作者id
     */
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    /**
     * 创建时间
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getTargetContent() {
        return targetContent;
    }

    public void setTargetContent(String targetContent) {
        this.targetContent = targetContent;
    }
}
