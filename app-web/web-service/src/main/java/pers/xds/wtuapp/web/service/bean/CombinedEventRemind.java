package pers.xds.wtuapp.web.service.bean;

import java.sql.Timestamp;

/**
 * @author DeSen Xu
 * @date 2023-01-14 14:07
 */
public class CombinedEventRemind {

    /**
     * 提醒id
     */
    private Integer id;

    /**
     * 同类型消息数量(可能为空)
     */
    private Integer count;

    /**
     * 提醒标题
     */
    private String remindTitle;

    /**
     * 发送者的id，若消息被聚合，则提供多个uid，否则只提供一个
     */
    private int[] senderIds;

    /**
     * @see pers.xds.wtuapp.web.database.bean.EventRemind#sourceId
     */
    private Integer sourceId;

    /**
     * @see pers.xds.wtuapp.web.database.bean.EventRemind#sourceType
     */
    private Integer sourceType;

    /**
     * @see pers.xds.wtuapp.web.database.bean.EventRemind#sourceContent
     */
    private String sourceContent;

    /**
     * @see pers.xds.wtuapp.web.database.bean.EventRemind#createTime
     */
    private Timestamp createTime;

    /**
     * @see pers.xds.wtuapp.web.database.bean.EventRemind#targetContent
     */
    private String targetContent;

    public String getTargetContent() {
        return targetContent;
    }

    public void setTargetContent(String targetContent) {
        this.targetContent = targetContent;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRemindTitle() {
        return remindTitle;
    }

    public void setRemindTitle(String remindTitle) {
        this.remindTitle = remindTitle;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceContent() {
        return sourceContent;
    }

    public void setSourceContent(String sourceContent) {
        this.sourceContent = sourceContent;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int[] getSenderIds() {
        return senderIds;
    }

    public void setSenderIds(int[] senderIds) {
        this.senderIds = senderIds;
    }
}
