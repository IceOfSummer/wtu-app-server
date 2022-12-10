package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.minidev.json.annotate.JsonIgnore;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;
import pers.xds.wtuapp.web.database.group.InsertGroup;
import pers.xds.wtuapp.web.database.group.UpdateGroup;

import javax.validation.constraints.*;
import java.sql.Timestamp;

/**
 * 商品实体类
 * @author DeSen Xu
 * @date 2022-09-09 11:14
 */
@TableName("commodity")
public class Commodity {

    /**
     * 当前商品正在正常出售
     */
    public static final int STATUS_ACTIVE = 0;

    /**
     * 当前商品已经下架了
     */
    public static final int STATUS_INACTIVE = 1;

    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
    @Null(groups = {InsertGroup.class, UpdateGroup.class})
    private Integer commodityId;

    /**
     * 卖家id
     */
    @Null(groups = {InsertGroup.class, UpdateGroup.class})
    private Integer ownerId;

    /**
     * 商品名称
     */
    @NotEmpty
    @Size(max = 30, groups = {InsertGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 商品描述
     */
    @NotEmpty
    @Size(max = 255, groups = {InsertGroup.class, UpdateGroup.class})
    private String description;

    /**
     * 商品创建时间
     */
    @Null(groups = {InsertGroup.class, UpdateGroup.class})
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createTime;

    /**
     * 售价
     */
    @NotNull(groups = {InsertGroup.class})
    @Min(value = 0, groups = {InsertGroup.class, UpdateGroup.class})
    private Double price;

    /**
     * 商品状态
     * 0: 正常，1:已被购买但交易还未完成，2:已被购买并且交易完成
     */
    @Null(groups = {InsertGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 交易地点
     */
    @NotNull(groups = {InsertGroup.class})
    @Size(max = 50, groups = {InsertGroup.class, UpdateGroup.class})
    private String tradeLocation;

    /**
     * 预览图片(商品列表预览图片)
     */
    @NotNull(groups = {InsertGroup.class})
    @Null(groups = {UpdateGroup.class})
    private String previewImage;

    /**
     * 实物图片
     */
    @NotNull(groups = {InsertGroup.class})
    @Null(groups = {UpdateGroup.class})
    private String images;

    /**
     * 乐观锁
     */
    @Version
    @Null(groups = {InsertGroup.class, UpdateGroup.class})
    @JsonIgnore
    private Integer version;

    /**
     * 商品数量
     */
    @Min(value = 1, groups = {InsertGroup.class, UpdateGroup.class})
    @NotNull(groups = {InsertGroup.class})
    private Integer count;

    public Commodity() {
    }

    public Commodity(Integer ownerId, String name, String description, Double price, String tradeLocation, String previewImage, String images) {
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.tradeLocation = tradeLocation;
        this.previewImage = previewImage;
        this.images = images;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTradeLocation() {
        return tradeLocation;
    }

    public void setTradeLocation(String tradeLocation) {
        this.tradeLocation = tradeLocation;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


}
