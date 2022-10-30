package pers.xds.wtuapp.im.database.bean;

import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * @author HuPeng
 * @date 2022-10-29 18:36
 */
@TableName("message")
public class UserMessage {

    private Integer msgId;

    private Integer msgTo;

    private Integer msgFrom;

    private String content;

    private Timestamp createTime;

    public Integer getMsgId() {
        return msgId;
    }

    public UserMessage() {
    }

    public UserMessage(Integer msgTo, Integer msgFrom, String content) {
        this.msgTo = msgTo;
        this.msgFrom = msgFrom;
        this.content = content;
        this.createTime = new Timestamp(System.currentTimeMillis());
    }

    public UserMessage(Integer msgId, Integer msgTo, Integer msgFrom, String content) {
        this.msgId = msgId;
        this.msgTo = msgTo;
        this.msgFrom = msgFrom;
        this.content = content;
        this.createTime = new Timestamp(System.currentTimeMillis());
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getMsgTo() {
        return msgTo;
    }

    public void setMsgTo(Integer msgTo) {
        this.msgTo = msgTo;
    }

    public Integer getMsgFrom() {
        return msgFrom;
    }

    public void setMsgFrom(Integer msgFrom) {
        this.msgFrom = msgFrom;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
