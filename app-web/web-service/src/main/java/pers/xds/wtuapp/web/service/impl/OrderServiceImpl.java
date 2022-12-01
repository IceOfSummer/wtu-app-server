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
import pers.xds.wtuapp.web.service.OrderService;
import pers.xds.wtuapp.web.database.view.OrderDetail;
import pers.xds.wtuapp.web.service.ServiceCode;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-03 16:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    private OrderDetailMapper orderDetailMapper;

    private UserTradeMapper userTradeMapper;

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

    @Autowired
    public void setUserTradeMapper(UserTradeMapper userTradeMapper) {
        this.userTradeMapper = userTradeMapper;
    }

    @Autowired
    public void setOrderDetailMapper(OrderDetailMapper orderDetailMapper) {
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    public List<OrderDetail> getUserOrderDetails(int userid, int page, int size) {
        Page<OrderDetail> pg = new Page<>(page, size);
        return orderDetailMapper.selectAllOrder(userid, pg).getRecords();
    }

    @Override
    public List<OrderDetail> getUserSoldOrder(int uid, int page, int size) {
        Page<OrderDetail> pg = new Page<>(page, size);
        return orderDetailMapper.selectAllSoldOrder(uid, pg).getRecords();
    }

    @Override
    public List<OrderDetail> getUserActiveOrderDetails(int uid) {
        return orderDetailMapper.selectOrders(uid, UserTrade.STATUS_TRADING, new Page<>(0, 50L)).getRecords();
    }

    @Override
    public List<OrderDetail> getUserPendingReceiveOrder(int uid) {
        return orderDetailMapper.selectActiveOrderByType(uid, UserTrade.TYPE_BUY);
    }

    @Override
    public List<OrderDetail> getUserPendingDeliveryOrder(int uid) {
        return orderDetailMapper.selectActiveOrderByType(uid, UserTrade.TYPE_SELL);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCode markTradeDone(int buyerId, int sellerId, int orderId, @Nullable String remark) {
        int i = userTradeMapper.modifyTradeStatus(buyerId, orderId, UserTrade.TYPE_BUY, UserTrade.STATUS_DONE);
        if (i == 0) {
            return ServiceCode.NOT_EXIST;
        }
        userTradeMapper.modifyTradeStatus(sellerId, orderId, UserTrade.TYPE_SELL, UserTrade.STATUS_DONE);
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
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return ServiceCode.NOT_EXIST;
        }
        // 确认用户是买家或卖家
        if (!(order.getOwnerId() == userId || order.getCustomerId() == userId)) {
            return ServiceCode.NOT_EXIST;
        }
        userTradeMapper.modifyTradeStatus(order.getOwnerId(), orderId, UserTrade.TYPE_SELL, UserTrade.STATUS_FAIL);
        userTradeMapper.modifyTradeStatus(order.getCustomerId(), orderId, UserTrade.TYPE_BUY, UserTrade.STATUS_FAIL);
        FinishedTrade finishedTrade = new FinishedTrade();
        finishedTrade.orderId = orderId;
        finishedTrade.fail = true;
        finishedTrade.remark = remark;
        finishedTradeMapper.insert(finishedTrade);
        // 把库存补上
        Commodity commodity = commodityMapper.selectById(order.getCommodityId());
        int i = commodityMapper.updateCommodity(order.getCommodityId(), order.getCount() + commodity.getCount(), commodity.getVersion());
        if (i == 0) {
            return ServiceCode.CONCURRENT_ERROR;
        }
        return ServiceCode.SUCCESS;
    }


}
