package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 用户交易记录表
 * @author HuPeng
 * @TableName user_trade
 */
@TableName(value ="user_trade")
public class UserTrade implements Serializable {
    /**
     * 用户id
     */
    @TableId
    private Integer userId;

    /**
     * 订单id
     */
    private Integer orderId;

    public static final int STATUS_TRADING = 0;

    public static final int STATUS_DONE = 1;

    public static final int STATUS_FAIL = 2;

    private Integer status;

    /**
     * 交易类型;0为买，1为卖
     */
    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 订单id
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 订单id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 交易类型;0为买，1为卖
     */
    public Integer getType() {
        return type;
    }

    /**
     * 交易类型;0为买，1为卖
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}