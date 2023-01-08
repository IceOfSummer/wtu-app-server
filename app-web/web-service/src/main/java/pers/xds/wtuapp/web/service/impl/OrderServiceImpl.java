package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.bean.UserTrade;
import pers.xds.wtuapp.web.database.mapper.*;
import pers.xds.wtuapp.web.database.view.OrderDetail;
import pers.xds.wtuapp.web.database.view.OrderPreview;
import pers.xds.wtuapp.web.service.OrderService;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.bean.UpdateOrderStatusParam;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-03 16:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    private OrderMapper orderMapper;

    private CommodityMapper commodityMapper;

    @Autowired
    public void setCommodityMapper(CommodityMapper commodityMapper) {
        this.commodityMapper = commodityMapper;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderPreview> getUserOrderDetails(int userid, int page, int size) {
        Page<OrderPreview> pg = new Page<>(page, size);
        return orderMapper.selectAllOrder(userid, pg).getRecords();
    }

    @Override
    public List<OrderPreview> getUserSoldOrder(int uid, int page, int size) {
        Page<OrderPreview> pg = new Page<>(page, size);
        return orderMapper.selectAllSoldOrder(uid, pg).getRecords();
    }

    @Override
    public List<OrderPreview> getUserActiveOrderDetails(int uid) {
        return orderMapper.selectOrders(uid, Order.STATUS_TRADING, new Page<>(0, 50L)).getRecords();
    }

    @Override
    public List<OrderPreview> getUserPendingReceiveOrder(int uid) {
        return orderMapper.selectActiveOrderByType(uid, UserTrade.TYPE_BUY);
    }

    @Override
    public List<OrderPreview> getUserPendingDeliveryOrder(int uid) {
        return orderMapper.selectActiveOrderByType(uid, UserTrade.TYPE_SELL);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCode markTradeDone(UpdateOrderStatusParam updateOrderStatusParam) {
        int newStatus;
        int prevStatus = updateOrderStatusParam.previousStatus;
        if (prevStatus == Order.STATUS_TRADING) {
            newStatus = updateOrderStatusParam.isSeller ? Order.STATUS_SELLER_CONFIRMED : Order.STATUS_BUYER_CONFIRMED;
        } else if (prevStatus == Order.STATUS_SELLER_CONFIRMED || prevStatus == Order.STATUS_BUYER_CONFIRMED) {
            newStatus = Order.STATUS_DONE;
        } else {
            // prevStatus = 取消订单 || prevStatus = Done
            return ServiceCode.NOT_AVAILABLE;
        }
        int i = orderMapper.updateTradeStatus(
                updateOrderStatusParam.orderId,
                updateOrderStatusParam.uid,
                newStatus,
                updateOrderStatusParam.previousStatus,
                updateOrderStatusParam.isSeller
        );
        // 插入备注
        updateRemark(updateOrderStatusParam);
        if (i == 0) {
            return ServiceCode.NOT_EXIST;
        }
        if (newStatus == Order.STATUS_DONE) {
            orderMapper.updateFinishedTime(updateOrderStatusParam.orderId);
        }
        return ServiceCode.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCode markTradeFail(UpdateOrderStatusParam updateOrderStatusParam) {
        int newStatus;
        boolean isCanceled = false;
        int prevStatus = updateOrderStatusParam.previousStatus;
        boolean isSeller = updateOrderStatusParam.isSeller;
        if (prevStatus == Order.STATUS_TRADING) {
            newStatus = updateOrderStatusParam.isSeller ? Order.STATUS_SELLER_CANCEL : Order.STATUS_BUYER_CANCEL;
        } else if (prevStatus == Order.STATUS_BUYER_CANCEL && isSeller) {
            newStatus = Order.STATUS_CANCELED_BY_BUYER;
            isCanceled = true;
        } else if (prevStatus == Order.STATUS_SELLER_CANCEL && !isSeller) {
            newStatus = Order.STATUS_CANCELED_BY_SELLER;
            isCanceled = true;
        } else if (prevStatus == Order.STATUS_SELLER_CONFIRMED || prevStatus == Order.STATUS_BUYER_CONFIRMED){
            // 被某一方确认，但是另外一方想要取消
            newStatus = isSeller ? Order.STATUS_SELLER_CANCEL : Order.STATUS_BUYER_CANCEL;
        } else {
            return ServiceCode.NOT_AVAILABLE;
        }
        int i = orderMapper.updateTradeStatus(
                updateOrderStatusParam.orderId,
                updateOrderStatusParam.uid,
                newStatus,
                updateOrderStatusParam.previousStatus,
                updateOrderStatusParam.isSeller
        );
        if (i == 0) {
            return ServiceCode.NOT_EXIST;
        }
        updateRemark(updateOrderStatusParam);
        if (isCanceled) {
            Order order = orderMapper.selectByIdSimply(updateOrderStatusParam.orderId);
            // 把库存补上
            commodityMapper.incrementCommodityCount(order.getCommodityId(), order.getCount());
            orderMapper.updateFinishedTime(updateOrderStatusParam.orderId);
        }
        return ServiceCode.SUCCESS;
    }

    private void updateRemark(UpdateOrderStatusParam param) {
        if (param.remark != null && !param.remark.isEmpty()) {
            if (param.isSeller) {
                orderMapper.updateSellerRemark(
                        param.uid,
                        param.orderId,
                        param.remark
                );
            } else {
                orderMapper.updateBuyerRemark(
                        param.uid,
                        param.orderId,
                        param.remark
                );
            }
        }
    }

    @Override
    public OrderDetail queryOrderDetail(int userId, int orderId) {
        return orderMapper.selectOrderDetailById(userId, orderId);
    }


}
