package pers.xds.wtuapp.web.database.view;

/**
 * @author DeSen Xu
 * @see pers.xds.wtuapp.web.database.bean.MessageTip#payload
 * @date 2023-01-11 15:33
 */
public class CommodityTipPayload {

    private int orderId;

    public CommodityTipPayload(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
