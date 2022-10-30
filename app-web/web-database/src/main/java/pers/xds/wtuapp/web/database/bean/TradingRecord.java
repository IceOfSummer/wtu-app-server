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
 * 正在交易中的订单;正在交易中的订单，建议在夜间清除所有valid为true的订单
 * @author DeSen Xu
 */
@TableName(value ="trading_record")
public class TradingRecord implements Serializable {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    /**
     * 
     */
    private Integer commodityId;

    /**
     * 
     */
    private Integer customerId;

    /**
     *
     */
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createTime;

    /**
     * 是否有效;订单完成或者订单取消，都会将该字段设置为false
     */
    private Boolean valid;

    /**
     * 用户备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public TradingRecord(Integer commodityId, Integer customerId, String remark) {
        this.commodityId = commodityId;
        this.customerId = customerId;
        this.remark = remark;
    }

    public TradingRecord() {
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
     * 
     */
    public Integer getCommodityId() {
        return commodityId;
    }

    /**
     * 
     */
    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    /**
     * 
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * 是否有效;订单完成或者订单取消，都会将该字段设置为true
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * 是否有效;订单完成或者订单取消，都会将该字段设置为true
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * 用户备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 用户备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                ", orderId=" + orderId +
                ", commodityId=" + commodityId +
                ", customerId=" + customerId +
                ", createTime=" + createTime +
                ", valid=" + valid +
                ", remark=" + remark +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}