package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.bean.UserTrade;
import pers.xds.wtuapp.web.database.mapper.*;
import pers.xds.wtuapp.web.database.view.OrderDetail;
import pers.xds.wtuapp.web.database.view.OrderPreview;
import pers.xds.wtuapp.web.es.dao.EsCommodityDao;
import pers.xds.wtuapp.web.service.OrderService;
import pers.xds.wtuapp.web.service.bean.UpdateOrderStatusParam;
import pers.xds.wtuapp.web.service.exception.BadArgumentException;
import pers.xds.wtuapp.web.service.exception.NoSuchElementException;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-03 16:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    private OrderMapper orderMapper;

    private CommodityMapper commodityMapper;

    private TradeStatMapper tradeStatMapper;

    private EsCommodityDao esCommodityDao;

    @Autowired
    public void setCommodityDao(EsCommodityDao esCommodityDao) {
        this.esCommodityDao = esCommodityDao;
    }

    @Autowired
    public void setTradeStatMapper(TradeStatMapper tradeStatMapper) {
        this.tradeStatMapper = tradeStatMapper;
    }

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
        Page<OrderPreview> pg = new Page<>(page, size, false);
        return orderMapper.selectAllOrder(userid, pg).getRecords();
    }

    @Override
    public List<OrderPreview> getUserSoldOrder(int uid, int page, int size) {
        Page<OrderPreview> pg = new Page<>(page, size, false);
        return orderMapper.selectAllSoldOrder(uid, pg).getRecords();
    }

    @Override
    public List<OrderPreview> getUserActiveOrderDetails(int uid) {
        return orderMapper.selectOrders(uid, Order.STATUS_TRADING, new Page<>(0, 50L, false)).getRecords();
    }

    @Override
    public List<OrderPreview> getUserPendingReceiveOrder(int uid, int page, int size) {
        return orderMapper.selectActiveOrderByType(uid, UserTrade.TYPE_BUY, new Page<>(page, size, false)).getRecords();
    }

    @Override
    public List<OrderPreview> getUserPendingDeliveryOrder(int uid, int page, int size) {
        return orderMapper.selectActiveOrderByType(uid, UserTrade.TYPE_SELL, new Page<>(page, size, false)).getRecords();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markTradeDone(UpdateOrderStatusParam updateOrderStatusParam) {
        int newStatus;
        int prevStatus = updateOrderStatusParam.previousStatus;
        if (prevStatus == Order.STATUS_TRADING) {
            newStatus = updateOrderStatusParam.isSeller ? Order.STATUS_SELLER_CONFIRMED : Order.STATUS_BUYER_CONFIRMED;
        } else if (prevStatus == Order.STATUS_SELLER_CONFIRMED || prevStatus == Order.STATUS_BUYER_CONFIRMED) {
            newStatus = Order.STATUS_DONE;
        } else {
            // prevStatus = 取消订单 || prevStatus = Done
            throw new BadArgumentException("订单已被取消");
        }
        int i = orderMapper.updateTradeStatus(
                updateOrderStatusParam.orderId,
                updateOrderStatusParam.uid,
                newStatus,
                updateOrderStatusParam.previousStatus,
                updateOrderStatusParam.isSeller
        );
        if (i == 0) {
            throw new NoSuchElementException("订单不存在");
        }
        // 插入备注
        updateRemark(updateOrderStatusParam);
        if (newStatus == Order.STATUS_DONE) {
            updateTradeStat(updateOrderStatusParam.orderId, null);
            orderMapper.updateFinishedTime(updateOrderStatusParam.orderId);
            Order order = orderMapper.selectByIdSimply(updateOrderStatusParam.orderId);
            commodityMapper.takeDownCommodityIfEmpty(order.getCommodityId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markTradeFail(UpdateOrderStatusParam updateOrderStatusParam) {
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
            throw new BadArgumentException("当前订单已经完成");
        }
        int i = orderMapper.updateTradeStatus(
                updateOrderStatusParam.orderId,
                updateOrderStatusParam.uid,
                newStatus,
                updateOrderStatusParam.previousStatus,
                updateOrderStatusParam.isSeller
        );
        if (i == 0) {
            throw new java.util.NoSuchElementException("订单不存在");
        }
        updateRemark(updateOrderStatusParam);
        if (isCanceled) {
            Order order = orderMapper.selectByIdSimply(updateOrderStatusParam.orderId);
            updateTradeStat(updateOrderStatusParam.orderId, order);
            // 把库存补上
            commodityMapper.incrementCommodityCount(order.getCommodityId(), order.getCount());
            orderMapper.updateFinishedTime(updateOrderStatusParam.orderId);
            esCommodityDao.increaseCommodityCount(order.getCommodityId(), order.getCount());
        }
    }

    /**
     * 更新交易统计
     * @param orderId 订单id
     * @param order 包含了买家和卖家id的订单对象，可以为空
     */
    private void updateTradeStat(int orderId, @Nullable Order order) {
        if (order == null) {
            order = orderMapper.selectByIdSimply(orderId);
        }
        int seller = order.getSellerId();
        int buyer = order.getBuyerId();
        tradeStatMapper.modifyDeliveryCount(seller, -1);
        tradeStatMapper.modifyReceiveCount(buyer, -1);
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
