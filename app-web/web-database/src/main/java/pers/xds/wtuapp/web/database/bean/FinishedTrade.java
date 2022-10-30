package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 交易完成的订单
 * @author DeSen Xu
 */
@TableName(value ="finished_trade")
public class FinishedTrade implements Serializable {
    /**
     * 订单id
     */
    @TableId
    private Integer orderId;

    /**
     * 卖家id
     */
    private Integer ownerId;

    /**
     * 买家id
     */
    private Integer customerId;

    /**
     * 商品id
     */
    private Integer commodityId;

    /**
     * 是否为失败的订单
     */
    private Integer fail;

    /**
     * 备注;对订单状态的备注，成功：买家备注，失败：买家取消原因
     */
    private String remark;

    /**
     * 完成时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
     * 买家id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 买家id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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
     * 是否为失败的订单
     */
    public Integer getFail() {
        return fail;
    }

    /**
     * 是否为失败的订单
     */
    public void setFail(Integer fail) {
        this.fail = fail;
    }

    /**
     * 备注;对订单状态的备注，成功：买家备注，失败：买家取消原因
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注;对订单状态的备注，成功：买家备注，失败：买家取消原因
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 完成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 完成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                ", orderId=" + orderId +
                ", customerId=" + customerId +
                ", commodityId=" + commodityId +
                ", fail=" + fail +
                ", remark=" + remark +
                ", createTime=" + createTime +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}