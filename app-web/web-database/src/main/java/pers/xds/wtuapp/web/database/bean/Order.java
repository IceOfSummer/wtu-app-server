package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;

import java.sql.Timestamp;

/**
 * 订单表
 * @author HuPeng
 * @TableName order
 */
@TableName(value ="`order`")
public class Order  {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    /**
     * 商品id
     */
    private Integer commodityId;

    /**
     * 顾客id
     */
    private Integer buyerId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 卖家id
     */
    private Integer sellerId;

    /**
     * 创建时间
     */
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createTime;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 订单还在交易中
     */
    public static final int STATUS_TRADING = 0;

    /**
     * 买家确定收货
     */
    public static final int STATUS_BUYER_CONFIRMED = 1;

    /**
     * 卖家确定发货
     */
    public static final int STATUS_SELLER_CONFIRMED = 2;

    /**
     * 买家取消订单
     */
    public static final int STATUS_BUYER_CANCEL = 3;

    /**
     * 卖家取消订单
     */
    public static final int STATUS_SELLER_CANCEL = 4;

    /**
     * 订单完成
     */
    public static final int STATUS_DONE = 100;

    /**
     * 被卖家主动取消
     */
    public static final int STATUS_CANCELED_BY_SELLER = 101;

    /**
     * 被买家主动取消
     */
    public static final int STATUS_CANCELED_BY_BUYER = 102;

    /**
     * 订单状态。当订单是完成状态时，<b>保证其值大于等于100</b>
     */
    private Integer status;

    public Order() {
    }

    /**
     * 数据库用
     */
    public Order(Integer commodityId, Integer buyerId, String remark, Integer sellerId, int count) {
        this.commodityId = commodityId;
        this.buyerId = buyerId;
        this.remark = remark;
        this.sellerId = sellerId;
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
     * 商品id
     */
    public Integer getCommodityId() {
        return commodityId;
    }

    /**
     * 商品id
     */
    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    /**
     * 顾客id
     */
    public Integer getBuyerId() {
        return buyerId;
    }

    /**
     * 顾客id
     */
    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 创建时间
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
