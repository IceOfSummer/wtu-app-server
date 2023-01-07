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
    private Integer customerId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 卖家id
     */
    private Integer ownerId;

    /**
     * 创建时间
     */
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createTime;

    /**
     * 商品数量
     */
    private Integer count;

    public static final int STATUS_TRADING = 0;

    public static final int STATUS_DONE = 1;

    public static final int STATUS_FAIL = 2;

    /**
     * 订单状态
     * @see Order#STATUS_DONE
     * @see Order#STATUS_FAIL
     * @see Order#STATUS_TRADING
     */
    private Integer status;

    public Order() {
    }

    /**
     * 数据库用
     */
    public Order(Integer commodityId, Integer customerId, String remark, Integer ownerId, int count) {
        this.commodityId = commodityId;
        this.customerId = customerId;
        this.remark = remark;
        this.ownerId = ownerId;
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
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 顾客id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
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
