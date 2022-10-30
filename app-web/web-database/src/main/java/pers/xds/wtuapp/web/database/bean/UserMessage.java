package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * @author DeSen Xu
 * @date 2022-09-03 17:03
 */
@TableName("message")
public class UserMessage {

    /**
     * 消息id，若为-1表示即时消息(双方都在线时发送的消息, 并没有存入数据库)
     */
    @TableField("msg_id")
    private Integer msgId;

    @TableField("msg_to")
    private Integer msgTo;

    @TableField("msg_from")
    private Integer msgFrom;

    @TableField("content")
    private String content;

    @TableField("create_time")
    private Timestamp createTime;

    public UserMessage() {
    }

    public UserMessage(int msgId, int to, int msgFrom, String content, Timestamp createTime) {
        this.msgId = msgId;
        this.msgTo = to;
        this.msgFrom = msgFrom;
        this.content = content;
        this.createTime = createTime;
    }

    public UserMessage(int msgId, int to, int msgFrom, String content) {
        this.msgId = msgId;
        this.msgTo = to;
        this.msgFrom = msgFrom;
        this.content = content;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getTo() {
        return msgTo;
    }

    public void setTo(Integer to) {
        this.msgTo = to;
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

    @Override
    public String toString() {
        return "UserMessage{" +
                "msgId=" + msgId +
                ", to=" + msgTo +
                ", msgFrom=" + msgFrom +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
