package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 订单表
 * @author HuPeng
 * @TableName order
 */
@TableName(value ="`order`")
public class Order implements Serializable {
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Order() {
    }

    public Order(Integer commodityId, Integer customerId, String remark, Integer ownerId) {
        this.commodityId = commodityId;
        this.customerId = customerId;
        this.remark = remark;
        this.ownerId = ownerId;
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
}