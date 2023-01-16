package pers.xds.wtuapp.web.service.config.email;


import pers.xds.wtuapp.web.database.util.Jackson;

/**
 * @author DeSen Xu
 * @date 2022-12-04 23:14
 */
public class CommodityLockTemplateData {

    public String username;

    public String itemName;

    public double price;

    public String tradeLocation;

    public String createTime;

    public String remark;

    public int count;

    public String image;

    /**
     * @param image 该参数不需要添加域名，使用时会自动添加
     */
    public CommodityLockTemplateData(String username, String itemName, double price, String tradeLocation, String createTime, String remark, int count, String image) {
        this.username = username;
        this.itemName = itemName;
        this.price = price;
        this.tradeLocation = tradeLocation;
        this.createTime = createTime;
        this.remark = remark;
        this.count = count;
        this.image = image;
    }

    public String toJson() {
        return Jackson.toJsonString(this);
    }
}
