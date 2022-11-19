package pers.xds.wtuapp.web.database.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.bean.UserTrade;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;

import java.sql.Timestamp;

/**
 * 订单详细记录
 * @author DeSen Xu
 * @date 2022-11-03 15:57
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetail {

    private int orderId;

    private int commodityId;

    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createTime;

    private String remark;

    private String name;

    private double price;

    private String tradeLocation;

    /**
     * @see UserTrade#getStatus()
     */
    private Integer status;

    /**
     * @see UserTrade#getType()
     */
    private Integer type;

    public OrderDetail() {
    }

    public OrderDetail(Order order, Commodity commodity) {
        this.orderId = order.getOrderId();
        this.commodityId = commodity.getCommodityId();
        this.createTime = order.getCreateTime();
        this.remark = order.getRemark();
        this.name = commodity.getName();
        this.price = commodity.getPrice();
        this.tradeLocation = commodity.getTradeLocation();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTradeLocation() {
        return tradeLocation;
    }

    public void setTradeLocation(String tradeLocation) {
        this.tradeLocation = tradeLocation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", commodityId=" + commodityId +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", tradeLocation='" + tradeLocation + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
