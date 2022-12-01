package pers.xds.wtuapp.web.service;

import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.database.view.OrderDetail;

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
    List<OrderDetail> getUserOrderDetails(int userid, int page, int size);

    /**
     * 获取用户商品出售记录
     * @param uid 用户id
     * @param page 第几页，从0开始
     * @param size 每页的大小
     * @return 出售记录
     */
    List<OrderDetail> getUserSoldOrder(int uid, int page, int size);

    /**
     * 获取用户当前正激活的订单
     * @param uid 用户id
     * @return 正激活的订单
     */
    List<OrderDetail> getUserActiveOrderDetails(int uid);

    /**
     * 获取用户待收货商品
     * @param uid 用户id
     * @return 待收货商品
     */
    List<OrderDetail> getUserPendingReceiveOrder(int uid);


    /**
     * 获取用户代发货界面
     * @param uid 用户id
     * @return 待发货商品
     */
    List<OrderDetail> getUserPendingDeliveryOrder(int uid);


    /**
     * 标记交易完成
     * @param buyerId 买家id
     * @param sellerId 卖家id
     * @param orderId 订单id
     * @param remark 用户备注，可能为空
     * @return 服务码<p>
     * - {@link ServiceCode#NOT_EXIST}表示订单不存在
     */
    ServiceCode markTradeDone(int buyerId, int sellerId, int orderId, @Nullable String remark);

    /**
     * 买家或卖家标记交易失败
     * @param userId 用户id
     * @param orderId 订单id
     * @param remark 备注
     * @return 服务码<p>
     * - {@link ServiceCode#NOT_EXIST}表示订单不存在<p>
     * - {@link ServiceCode#CONCURRENT_ERROR}乐观锁冲突，稍后再试<p>
     */
    ServiceCode markTradeFail(int userId, int orderId, @Nullable String remark);
}
