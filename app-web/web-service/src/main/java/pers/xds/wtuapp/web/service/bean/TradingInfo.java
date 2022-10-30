package pers.xds.wtuapp.web.service.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.bean.TradingRecord;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;
import pers.xds.wtuapp.web.database.mapper.CommodityMapper;

import java.sql.Timestamp;

/**
 * 正在进行的交易记录
 * @author DeSen Xu
 * @date 2022-10-01 16:36
 */
public class TradingInfo {

    /**
     * 需要从commodity表中拿去的列
     */
    public static final String[] REQUIRED_COMMODITY_COLUMN = {
            CommodityMapper.COLUMN_OWNER_ID,
            CommodityMapper.COLUMN_NAME,
            CommodityMapper.COLUMN_PRICE,
            CommodityMapper.COLUMN_PREVIEW_IMAGE,
            CommodityMapper.COLUMN_TRADE_LOCATION
    };

    private int orderId;

    private int commodityId;

    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createTime;

    private String remark;

    private int ownerId;

    private String name;

    private double price;

    private String previewImage;

    private String tradeLocation;


    public TradingInfo(Commodity commodity, TradingRecord record) {
        this.orderId = record.getOrderId();
        this.commodityId = record.getCommodityId();
        this.createTime = record.getCreateTime();
        this.remark = record.getRemark();
        this.ownerId = commodity.getOwnerId();
        this.name = commodity.getName();
        this.price = commodity.getPrice();
        this.previewImage = commodity.getPreviewImage();
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String getTradeLocation() {
        return tradeLocation;
    }

    public void setTradeLocation(String tradeLocation) {
        this.tradeLocation = tradeLocation;
    }

    @Override
    public String toString() {
        return "TradingInfo{" +
                "orderId=" + orderId +
                ", commodityId=" + commodityId +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", previewImage='" + previewImage + '\'' +
                ", tradeLocation='" + tradeLocation + '\'' +
                '}';
    }
}
