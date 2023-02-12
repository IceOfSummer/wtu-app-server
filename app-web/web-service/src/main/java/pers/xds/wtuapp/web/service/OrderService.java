package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.database.view.OrderDetail;
import pers.xds.wtuapp.web.database.view.OrderPreview;
import pers.xds.wtuapp.web.service.bean.UpdateOrderStatusParam;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-03 15:53
 */
public interface OrderService {

    /**
     * 获取用户交易记录(不管是否已经完成)，即出售和购买的记录
     * @param userid 用户id
     * @param page 第几页, 从0开始
     * @param size 每页最多显示多少行
     * @return 正在交易的商品记录
     */
    List<OrderPreview> getUserOrderDetails(int userid, int page, int size);

    /**
     * 获取用户商品出售记录
     * @param uid 用户id
     * @param page 第几页，从0开始
     * @param size 每页的大小
     * @return 出售记录
     */
    List<OrderPreview> getUserSoldOrder(int uid, int page, int size);

    /**
     * 获取用户当前正激活的订单
     * @param uid 用户id
     * @return 正激活的订单
     */
    List<OrderPreview> getUserActiveOrderDetails(int uid);

    /**
     * 获取用户待收货商品
     * @param uid 用户id
     * @param page 查看第几页(从1开始)
     * @param size 每页大小
     * @return 待收货商品
     */
    List<OrderPreview> getUserPendingReceiveOrder(int uid, int page, int size);


    /**
     * 获取用户代发货界面
     * @param uid 用户id
     * @param page 查看第几页(从1开始)
     * @param size 每页大小
     * @return 待发货商品
     */
    List<OrderPreview> getUserPendingDeliveryOrder(int uid, int page, int size);


    /**
     * 买家或卖家标记交易完成
     * @param updateOrderStatusParam 参数
     * @return 服务码<p>
     * @throws pers.xds.wtuapp.web.service.exception.NoSuchElementException 表示订单不存在<p>
     * @throws pers.xds.wtuapp.web.service.exception.BadArgumentException 表示订单已经被某一方申请取消
     */
    void markTradeDone(UpdateOrderStatusParam updateOrderStatusParam);

    /**
     * 买家或卖家标记交易失败
     * @param updateOrderStatusParam 参数
     * @return 服务码<p>
     * @throws pers.xds.wtuapp.web.service.exception.NoSuchElementException 表示订单不存在
     * @throws pers.xds.wtuapp.web.service.exception.BadArgumentException 表示订单已经完成
     */
    void markTradeFail(UpdateOrderStatusParam updateOrderStatusParam);


    /**
     * 查询订单详细信息
     * @param userId 用户id
     * @param orderId 订单id
     * @return 订单详细信息，可能为空
     */
    OrderDetail queryOrderDetail(int userId, int orderId);

}
