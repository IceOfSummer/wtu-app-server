package pers.xds.wtuapp.web.es.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author DeSen Xu
 * @date 2022-09-10 17:21
 */
@Document(indexName = "commodity")
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Field(type = FieldType.Integer)
    private Integer count;

    public int getId() {
        return id;
    }

    public EsCommodity setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EsCommodity setName(String name) {
        this.name = name;
        return this;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public EsCommodity setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public EsCommodity setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getImage() {
        return image;
    }

    public EsCommodity setImage(String image) {
        this.image = image;
        return this;
    }

    public String getTradeLocation() {
        return tradeLocation;
    }

    public EsCommodity setTradeLocation(String tradeLocation) {
        this.tradeLocation = tradeLocation;
        return this;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public EsCommodity setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public String getSellerNickname() {
        return sellerNickname;
    }

    public EsCommodity setSellerNickname(String sellerNickname) {
        this.sellerNickname = sellerNickname;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public EsCommodity setCount(Integer count) {
        this.count = count;
        return this;
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
