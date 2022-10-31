package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;

import java.sql.Timestamp;

/**
 * 消息表
 * <p>
 * 复合主键(uid, msg_id)
 * @author DeSen Xu
 * @date 2022-10-31 15:09
 */
@TableName("message")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    private Integer uid;

    private Integer msgId;

    private Integer from;

    private String content;

    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createTime;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
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
