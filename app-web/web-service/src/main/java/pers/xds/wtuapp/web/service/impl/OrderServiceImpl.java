package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.bean.FinishedTrade;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.bean.UserTrade;
import pers.xds.wtuapp.web.database.mapper.*;
import pers.xds.wtuapp.web.database.view.OrderDetail;
import pers.xds.wtuapp.web.database.view.OrderPreview;
import pers.xds.wtuapp.web.service.OrderService;
import pers.xds.wtuapp.web.service.ServiceCode;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-03 16:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    private FinishedTradeMapper finishedTradeMapper;

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

    @Autowired
    public void setFinishedTradeMapper(FinishedTradeMapper finishedTradeMapper) {
        this.finishedTradeMapper = finishedTradeMapper;
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
    public ServiceCode markTradeDone(int uid, int orderId, @Nullable String remark) {
        int i = orderMapper.buyerUpdateTradeStatus(orderId, uid, Order.STATUS_DONE);
        if (i == 0) {
            return ServiceCode.NOT_EXIST;
        }
        FinishedTrade finishedTrade = new FinishedTrade();
        finishedTrade.orderId = orderId;
        finishedTrade.fail = false;
        finishedTrade.remark = remark;
        finishedTradeMapper.insert(finishedTrade);
        return ServiceCode.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCode markTradeFail(int userId, int orderId, @Nullable String remark) {
        Order order = orderMapper.selectByIdSimply(orderId);
        if (order == null) {
            return ServiceCode.NOT_EXIST;
        }
        // 确认用户是买家或卖家
        if (order.getOwnerId() == userId) {
            orderMapper.sellerUpdateTradeStatus(orderId, userId, Order.STATUS_FAIL);
        } else if (order.getCustomerId() == userId) {
            orderMapper.buyerUpdateTradeStatus(orderId, userId, Order.STATUS_FAIL);
        } else {
            return ServiceCode.NOT_EXIST;
        }
        FinishedTrade finishedTrade = new FinishedTrade();
        finishedTrade.orderId = orderId;
        finishedTrade.fail = true;
        finishedTrade.remark = remark;
        finishedTradeMapper.insert(finishedTrade);
        // 把库存补上
        Commodity commodity = commodityMapper.selectById(order.getCommodityId());
        int i = commodityMapper.updateCommodityCount(order.getCommodityId(), order.getCount() + commodity.getCount(), commodity.getVersion());
        if (i == 0) {
            return ServiceCode.CONCURRENT_ERROR;
        }
        return ServiceCode.SUCCESS;
    }

    @Override
    public OrderDetail queryOrderDetail(int userId, int orderId) {
        return orderMapper.selectOrderDetailById(userId, orderId);
    }


}
