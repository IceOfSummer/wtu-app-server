package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import net.minidev.json.annotate.JsonIgnore;

/**
 * 交易统计
 * @author DeSen Xu
 */
@TableName(value ="trade_stat")
public class TradeStat {
    /**
     * 用户id
     */
    @TableId
    @JsonIgnore
    private Integer userId;

    /**
     * 当前正在售卖的商品数量
     */
    private Integer sellingCount;

    /**
     * 待收货数量
     */
    private Integer receiveCount;

    /**
     * 待发货数量
     */
    private Integer deliveryCount;

    public TradeStat() {
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSellingCount() {
        return sellingCount;
    }

    public void setSellingCount(Integer sellingCount) {
        this.sellingCount = sellingCount;
    }

    public Integer getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(Integer receiveCount) {
        this.receiveCount = receiveCount;
    }

    public Integer getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Integer deliveryCount) {
        this.deliveryCount = deliveryCount;
    }
}
