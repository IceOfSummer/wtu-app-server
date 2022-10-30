package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author HuPeng
 * @date 2022-10-29 18:41
 */
@TableName("message_receive")
public class MessageReceive {

    @TableId
    private Integer uid;

    private Integer receiveId;

    public MessageReceive() {
    }

    public MessageReceive(Integer uid) {
        this.uid = uid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

}
