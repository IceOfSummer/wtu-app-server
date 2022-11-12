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

    @TableField(value = "`to`")
    private Integer to;

    /**
     * 消息id，自增
     */
    private Integer msgId;

    private String content;

    private Integer createTime;

    /**
     * 0为接收，1为发送
     */
    private Integer type;

    public Integer getMsgId() {
        return msgId;
    }

    public Message() {
    }

    public Message(Integer uid, Integer to, String content) {
        this.uid = uid;
        this.to = to;
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

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
