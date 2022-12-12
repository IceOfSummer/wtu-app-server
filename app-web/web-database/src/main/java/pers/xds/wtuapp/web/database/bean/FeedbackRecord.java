package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author DeSen Xu
 * @date 2022-12-12 22:15
 */
@TableName("feedback_record")
public class FeedbackRecord {

    private Integer uid;

    private Integer messageId;

    @TableField("`like`")
    private Boolean like;

    public FeedbackRecord() {
    }

    public FeedbackRecord(Integer uid, Integer messageId, Boolean like) {
        this.uid = uid;
        this.messageId = messageId;
        this.like = like;
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

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }
}
