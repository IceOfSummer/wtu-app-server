package pers.xds.wtuapp.web.es.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author DeSen Xu
 * @date 2022-09-10 17:21
 */
@Document(indexName = "commodity")
public class EsCommodity {

    @Id
    private int id;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Long, index = false)
    private Long createTime;

    @Field(type = FieldType.Double, index = false)
    private Double price;

    @Field(type = FieldType.Text, index = false)
    private String image;

    @Field(type = FieldType.Text, index = false)
    private String tradeLocation;

    @Field(type = FieldType.Integer, index = false)
    private Integer sellerId;

    @Field(type = FieldType.Text, index = false)
    private String sellerNickname;

    public EsCommodity(int id, String name, Long createTime, Double price, String image, String tradeLocation, Integer sellerId, String sellerNickname) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.price = price;
        this.image = image;
        this.tradeLocation = tradeLocation;
        this.sellerId = sellerId;
        this.sellerNickname = sellerNickname;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerNickname() {
        return sellerNickname;
    }

    public void setSellerNickname(String sellerNickname) {
        this.sellerNickname = sellerNickname;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTradeLocation() {
        return tradeLocation;
    }

    public void setTradeLocation(String tradeLocation) {
        this.tradeLocation = tradeLocation;
    }

    @Override
    public String toString() {
        return "EsCommodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                '}';
    }
}
