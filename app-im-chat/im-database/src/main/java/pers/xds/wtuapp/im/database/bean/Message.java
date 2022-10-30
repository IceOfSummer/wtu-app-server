package pers.xds.wtuapp.im.database.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * message表<p>
 *
 * <b>主键为联合索引(uid, msgId)</b>
 * @author HuPeng
 * @date 2022-10-29 18:36
 */
@TableName("message")
public class Message {

    private Integer uid;

    /**
     * 消息id，自增
     */
    private Integer msgId;

    @TableField(value = "`from`")
    private Integer from;

    private String content;

    private Timestamp createTime;

    public Integer getMsgId() {
        return msgId;
    }

    public Message() {
    }

    public Message(Integer uid, Integer from, String content) {
        this.uid = uid;
        this.from = from;
        this.content = content;
        this.createTime = new Timestamp(System.currentTimeMillis());
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
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
