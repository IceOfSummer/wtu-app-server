package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @author DeSen Xu
 * @date 2022-09-03 17:23
 */
public class MessageReceive {

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Integer userId;

    @TableField("receive_id")
    private Integer receiveId;

    @TableField("unreceived_id")
    private Integer unreceivedId;


    public MessageReceive() {
    }

    public MessageReceive(int userId) {
        this.userId = userId;
        this.receiveId =0;
        this.unreceivedId = 0;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public Integer getUnreceivedId() {
        return unreceivedId;
    }

    public void setUnreceivedId(Integer unreceivedId) {
        this.unreceivedId = unreceivedId;
    }
}
