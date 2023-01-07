package pers.xds.wtuapp.web.database.view;

import java.sql.Timestamp;

/**
 * 订单的详细信息
 * @author DeSen Xu
 * @date 2023-01-07 13:38
 */
public class OrderDetail {

    /**
     * 商品id
     */
    private Integer commodityId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 交易数量
     */
    private Integer count;

    /**
     * 交易价格
     */
    private Double price;

    /**
     * 交易地点
     */
    private String tradeLocation;

    /**
     * 交易状态
     * @see pers.xds.wtuapp.web.database.bean.Order#status
     */
    private Integer status;

    /**
     * 交易类型
     * @see pers.xds.wtuapp.web.database.bean.UserTrade#type
     */
    private Integer type;

    /**
     * 买家备注
     */
    private String remark;

    /**
     * 对方名称
     */
    private String tradeName;

    /**
     * 对方uid
     */
    private Integer tradeUid;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 交易完成时间
     */
    private Timestamp finishedTime;

    /**
     * 完成时的备注
     */
    private String finishedRemark;

    public Timestamp getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Timestamp finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getFinishedRemark() {
        return finishedRemark;
    }

    public void setFinishedRemark(String finishedRemark) {
        this.finishedRemark = finishedRemark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public Integer getTradeUid() {
        return tradeUid;
    }

    public void setTradeUid(Integer tradeUid) {
        this.tradeUid = tradeUid;
    }
}
