package pers.xds.wtuapp.im.database.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * message表<p>
 *
 * <b>主键为联合索引(uid, msgId)</b>
 * @author HuPeng
 * @date 2022-10-29 18:36
 */
@TableName("message")
public class Message implements Serializable {

    private static final long serialVersionUID = 123L;

    private Integer uid;

    /**
     * 消息id，自增
     */
    private Integer msgId;

    @TableField(value = "`from`")
    private Integer from;

    private String content;

    private Integer createTime;

    public Integer getMsgId() {
        return msgId;
    }

    public Message() {
    }

    public Message(Integer uid, Integer from, String content) {
        this(uid, null, from, content);
    }

    public Message(Integer uid, Integer msgId, Integer from, String content) {
        this.uid = uid;
        this.msgId = msgId;
        this.from = from;
        this.content = content;
        this.createTime = Math.toIntExact((System.currentTimeMillis() / 1000));
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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}
