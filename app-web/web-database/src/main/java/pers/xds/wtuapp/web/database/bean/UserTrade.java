package pers.xds.wtuapp.web.database.bean;

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

    public static final int TYPE_BUY = 0;

    public static final int TYPE_SELL = 1;

    /**
     * 交易类型;0为买，1为卖
     */
    private Integer type;

    /**
     * 交易对方的uid
     */
    private Integer tradeUid;

    /**
     * 交易对方的称呼
     */
    private String tradeName;

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

}
