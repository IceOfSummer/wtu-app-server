package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.bean.UserTrade;
import pers.xds.wtuapp.web.service.common.DataBeanCombiner;
import pers.xds.wtuapp.web.database.mapper.CommodityMapper;
import pers.xds.wtuapp.web.database.mapper.OrderMapper;
import pers.xds.wtuapp.web.database.mapper.UserTradeMapper;
import pers.xds.wtuapp.web.service.OrderService;
import pers.xds.wtuapp.web.service.bean.OrderDetail;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author DeSen Xu
 * @date 2022-11-03 16:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * 添加订单状态
     */
    private static final BiFunction<UserTrade, OrderDetail, OrderDetail> ATTACH_ORDER_STATUS = (userTrade, orderDetail) -> {
        orderDetail.setStatus(userTrade.getStatus());
        return orderDetail;
    };

    /**
     * 查询用户激活的订单时，仅查询前几次订单，然后取出其中激活的订单
     */
    private static final IPage<UserTrade> ACTIVE_PAGE_LIMITER = new Page<>(0, 50L);

    /**
     * 请确保Order能够拿到CommodityId，否则会报错!
     */
    private static final DataBeanCombiner<Order, Commodity, OrderDetail> ORDER_COMBINER =
            new DataBeanCombiner<>((order, commodity) -> {
                if (commodity == null) {
                    return null;
                }
                return new OrderDetail(order, commodity);
            }, (order, commodity) -> order.getCommodityId().intValue() == commodity.getCommodityId().intValue());


    private static final String[] COMMODITY_DATA_COLUMNS = new String[] {
            CommodityMapper.COLUMN_COMMODITY_ID,
            CommodityMapper.COLUMN_OWNER_ID,
            CommodityMapper.COLUMN_NAME,
            CommodityMapper.COLUMN_PRICE,
            CommodityMapper.COLUMN_PREVIEW_IMAGE,
            CommodityMapper.TRADE_LOCATION
    };

    private OrderMapper orderMapper;

    private UserTradeMapper userTradeMapper;

    private CommodityMapper commodityMapper;

    @Autowired
    public void setCommodityMapper(CommodityMapper commodityMapper) {
        this.commodityMapper = commodityMapper;
    }

    @Autowired
    public void setUserTradeMapper(UserTradeMapper userTradeMapper) {
        this.userTradeMapper = userTradeMapper;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }


    @Override
    public List<OrderDetail> getUserOrderDetails(int userid, int page, int size) {
        QueryWrapper<UserTrade> tradeWrapper = new QueryWrapper<UserTrade>()
                .select(UserTradeMapper.COLUMN_USER_ID, UserTradeMapper.COLUMN_ORDER_ID, UserTradeMapper.COLUMN_STATUS)
                .eq(UserTradeMapper.COLUMN_USER_ID, userid);

        List<UserTrade> userTrades = userTradeMapper
                .selectPage(new Page<>(page, size), tradeWrapper)
                .getRecords();

        List<Integer> orderIds = userTrades.stream()
                .map(UserTrade::getOrderId)
                .collect(Collectors.toList());

        return queryOrderDetails(orderIds, userTrades, ATTACH_ORDER_STATUS);
    }


    @Override
    public List<OrderDetail> getUserActiveOrderDetails(int uid) {
        QueryWrapper<UserTrade> tradeWrapper = new QueryWrapper<UserTrade>()
                .select(UserTradeMapper.COLUMN_USER_ID, UserTradeMapper.COLUMN_ORDER_ID)
                .eq(UserTradeMapper.COLUMN_USER_ID, uid)
                .eq(UserTradeMapper.COLUMN_STATUS, UserTrade.STATUS_TRADING);

        List<Integer> orderIds = userTradeMapper
                .selectPage(ACTIVE_PAGE_LIMITER, tradeWrapper)
                .getRecords()
                .stream()
                .map(UserTrade::getOrderId)
                .collect(Collectors.toList());

        return queryOrderDetails(orderIds, null, null);
    }

    private List<OrderDetail> queryOrderDetails(List<Integer> orderIds, List<UserTrade> attach, BiFunction<UserTrade, OrderDetail, OrderDetail> afterCombine) {
        if (orderIds.isEmpty()) {
            return Collections.emptyList();
        }

        QueryWrapper<Order> orderWrapper = new QueryWrapper<Order>()
                .select(OrderMapper.COLUMN_COMMODITY_ID,
                        OrderMapper.COLUMN_ORDER_ID,
                        OrderMapper.COLUMN_CREATE_TIME,
                        OrderMapper.COLUMN_REMARK)
                .in(OrderMapper.COLUMN_ORDER_ID, orderIds);

        List<Order> orders = orderMapper.selectList(orderWrapper);

        List<Integer> commodityIds = orders.stream()
                .map(Order::getCommodityId)
                .collect(Collectors.toList());

        QueryWrapper<Commodity> commodityQueryWrapper = new QueryWrapper<Commodity>()
                .select(COMMODITY_DATA_COLUMNS)
                .in(CommodityMapper.COLUMN_COMMODITY_ID, commodityIds);

        List<Commodity> commodities = commodityMapper.selectList(commodityQueryWrapper);

        if (orderIds.size() != commodities.size()) {
            log.warn("the database may inserted wrong data, method: getUserActiveOrderDetails, orderIds: {}", orderIds);
        }
        return ORDER_COMBINER.combine(orders, commodities, attach, afterCombine);
    }
}
