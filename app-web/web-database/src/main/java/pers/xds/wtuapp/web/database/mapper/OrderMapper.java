package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.view.OrderDetail;
import pers.xds.wtuapp.web.database.view.OrderPreview;


/**
* @author HuPeng
* @description 针对表【order(订单表)】的数据库操作Mapper
* @date  2022-11-03 15:53:00
* @Entity pers.xds.wtuapp.web.database.bean.Order
*/
@Mapper
public interface OrderMapper {

    /**
     * 插入一个订单
     * @param order 订单
     */
    void insert(@Param("o") Order order);

    /**
     * 获取用户订单的简要信息
     * @param uid 用户id
     * @param status 要选择哪个状态的订单, 传null时为全选。<p>
     *     {@link pers.xds.wtuapp.web.database.bean.UserTrade#TYPE_BUY}<p>
     *     {@link pers.xds.wtuapp.web.database.bean.UserTrade#TYPE_SELL}
     * @param page 分页
     * @return 订单预览
     */
    IPage<OrderPreview> selectOrders(@Param("uid") int uid, @Param("status") Integer status, IPage<OrderPreview> page);

    /**
     * 根据订单类型获取激活中的订单简要信息
     * @param uid 用户id
     * @param type 类型 {@link pers.xds.wtuapp.web.database.bean.UserTrade#type}
     * @return 订单信息，<b>type属性为null</b>
     */
    IPage<OrderPreview> selectActiveOrderByType(@Param("uid") int uid, @Param("type") int type, IPage<OrderPreview> page);

    /**
     * 获取所有订单简要信息
     * @param uid 用户id
     * @param page 分页
     * @return 订单信息
     */
    IPage<OrderPreview> selectAllOrder(@Param("uid") int uid, IPage<OrderPreview> page);

    /**
     * 获取用户所有激活中的出售订单
     * @param uid 用户id
     * @param page 分页
     * @return 所有出售订单
     */
    IPage<OrderPreview> selectAllSoldOrder(@Param("uid") int uid, IPage<OrderPreview> page);

    /**
     * 买家修改交易的状态
     * @param orderId 订单id
     * @param uid 用户id
     * @param status 订单状态 {@link Order#status}
     * @param previousStatus 之前的状态
     * @return 返回1表示成功，0表示失败(订单不存在).
     */
    int buyerUpdateTradeStatus(@Param("oid") int orderId, @Param("uid") int uid, @Param("s") int status, @Param("ps") int previousStatus);

    /**
     * 卖家修改交易状态
     * @param orderId 订单id
     * @param uid 用户id
     * @param status 要修改的状态 {@link Order#status}
     * @param previousStatus 之前的状态
     * @return 返回1表示成功，0表示失败(订单不存在).
     */
    int sellerUpdateTradeStatus(@Param("oid") int orderId, @Param("uid") int uid, @Param("s") int status, @Param("ps") int previousStatus);

    /**
     * 修改交易状态
     * @param orderId 订单id
     * @param uid 用户id
     * @param newStatus 要修改的状态 {@link Order#status}
     * @param previousStatus 之前的状态
     * @param isSeller 该用户是否为卖家
     * @return 返回1表示成功
     */
    int updateTradeStatus(
            @Param("oid") int orderId,
            @Param("uid") int uid,
            @Param("s") int newStatus,
            @Param("ps") int previousStatus,
            @Param("isSeller") boolean isSeller
    );

    /**
     * 获取订单的简要信息
     * @param orderId 订单id
     * @return 订单, 只有{@link Order#buyerId}, {@link Order#sellerId}, {@link Order#count}, {@link Order#commodityId}
     */
    Order selectByIdSimply(@Param("oid") int orderId);

    /**
     * 获取订单详细信息
     * @param userId 用户id
     * @param orderId 订单id
     * @return 订单详细信息
     */
    OrderDetail selectOrderDetailById(@Param("uid") int userId, @Param("oid") int orderId);

    /**
     * 更新订单完成时间
     * @param orderId 订单id
     * @return 返回1表示成功, 0表示失败
     */
    int updateFinishedTime(@Param("oid") int orderId);

    /**
     * 更新备注
     * @param userId 用户id
     * @param orderId 订单id
     * @param remark 备注
     * @return 返回1表示成功
     */
    int updateBuyerRemark(@Param("uid") int userId, @Param("oid") int orderId, @Param("r") String remark);

    /**
     * 更新卖家备注
     * @param userId 用户id
     * @param orderId 订单id
     * @param remark 备注
     * @return 返回1表示成功
     */
    int updateSellerRemark(@Param("uid") int userId, @Param("oid") int orderId, @Param("r") String remark);
}




