package pers.xds.wtuapp.web.database.view;

import java.sql.Timestamp;

/**
 * 订单预览实体类. 只包含了一些简要信息
 * @author DeSen Xu
 * @date 2023-01-06 15:53
 */
public class OrderPreview {

    /**
     * 对方id
     */
    private Integer tradeUid;

    /**
     * 对方昵称
     */
    private String tradeName;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 订单创建时间
     */
    private Timestamp createTime;

    /**
     * 预览图
     */
    private String previewImage;

    /**
     * 交易类型
     */
    private Integer type;

    /**
     * 价格
     */
    private Double price;

    /**
     * 交易数量
     */
    private Integer count;

    /**
     * 交易状态
     * @see pers.xds.wtuapp.web.database.bean.Order#status
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTradeUid() {
        return tradeUid;
    }

    public void setTradeUid(Integer tradeUid) {
        this.tradeUid = tradeUid;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
