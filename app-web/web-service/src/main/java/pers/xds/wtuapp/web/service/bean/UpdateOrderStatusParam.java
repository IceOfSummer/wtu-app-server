package pers.xds.wtuapp.web.service.bean;

/**
 * @author DeSen Xu
 * @date 2023-01-07 21:45
 */
public class UpdateOrderStatusParam {

    /**
     * 用户id
     */
    public int uid;

    /**
     * 订单id
     */
    public int orderId;

    /**
     * 是否为卖家
     */
    public boolean isSeller;

    /**
     * 之前的状态
     * @see pers.xds.wtuapp.web.database.bean.Order#status
     */
    public int previousStatus;

    /**
     * 备注
     */
    public String remark;

    public UpdateOrderStatusParam(int uid, int orderId, boolean isSeller, int previousStatus, String remark) {
        this.uid = uid;
        this.orderId = orderId;
        this.isSeller = isSeller;
        this.previousStatus = previousStatus;
        this.remark = remark;
    }
}
