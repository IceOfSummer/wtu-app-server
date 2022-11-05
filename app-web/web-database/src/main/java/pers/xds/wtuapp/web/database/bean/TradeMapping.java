package pers.xds.wtuapp.web.database.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;

import java.sql.Timestamp;

/**
 * 订单结果集，不存在对应的表
 * @see pers.xds.wtuapp.web.database.mapper.FinishedTradeMapper
 * @author DeSen Xu
 * @date 2022-09-18 21:43
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeMapping {

    /**
     * 交易id
     */
    private Integer orderId;

    /**
     * 商品id
     */
    private Integer commodityId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 预览图
     */
    private String previewImage;

    /**
     * 麦家id
     */
    private Integer ownerId;

    /**
     * 买家id
     */
    private Integer customerId;

    /**
     * 是否为失败的订单
     */
    private Boolean fail;

    /**
     * 备注
     */
    private String remark;

    /**
     * 完成时间
     */
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createTime;

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Boolean getFail() {
        return fail;
    }

    public void setFail(Boolean fail) {
        this.fail = fail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
