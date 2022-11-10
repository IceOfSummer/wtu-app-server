package pers.xds.wtuapp.im.database.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author DeSen Xu
 * @date 2022-11-10 17:28
 */
@TableName("message_receive")
public class MessageReceive {

    @TableId
    private Integer uid;

    private Integer receivedId;

    private Integer unreceivedId;

    public MessageReceive() {
    }

    /**
     * 用于数据库插入, 自动将receivedId置为1
     * @param uid 用户id
     */
    public MessageReceive(Integer uid) {
        this.uid = uid;
        this.receivedId = 1;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getReceivedId() {
        return receivedId;
    }

    public void setReceivedId(Integer receivedId) {
        this.receivedId = receivedId;
    }

    public Integer getUnreceivedId() {
        return unreceivedId;
    }

    public void setUnreceivedId(Integer unreceivedId) {
        this.unreceivedId = unreceivedId;
    }
}
