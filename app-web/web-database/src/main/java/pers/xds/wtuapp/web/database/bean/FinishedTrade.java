package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;

import java.sql.Timestamp;

/**
 * 交易完成的订单
 * @author DeSen Xu
 */
@TableName(value ="finished_trade")
public class FinishedTrade {
    /**
     * 订单id
     */
    @TableId
    public Integer orderId;

    /**
     * 是否为失败的订单
     */
    public Boolean fail;

    /**
     * 备注;对订单状态的备注，成功：买家备注，失败：买家取消原因
     */
    public String remark;

    /**
     * 完成时间
     */
    @JsonSerialize(using = TimestampSerializer.class)
    public Timestamp createTime;

}