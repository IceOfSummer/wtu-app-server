package pers.xds.wtuapp.web.database.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    public int orderId;

    public int commodityId;

    @JsonSerialize(using = TimestampSerializer.class)
    public Timestamp createTime;

    public String remark;

    public String name;

    public double price;

    public String tradeLocation;

    /**
     * @see UserTrade#getStatus()
     */
    public Integer status;

    /**
     * @see UserTrade#getType()
     */
    public Integer type;

    public Integer count;

    public String previewImage;

    public Integer ownerId;

}
