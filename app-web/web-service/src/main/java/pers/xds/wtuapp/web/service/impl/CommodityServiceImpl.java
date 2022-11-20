package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.bean.TradeStat;
import pers.xds.wtuapp.web.database.mapper.OrderMapper;
import pers.xds.wtuapp.web.database.mapper.TradeStatMapper;
import pers.xds.wtuapp.web.database.mapper.UserTradeMapper;
import pers.xds.wtuapp.web.service.CommodityService;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.mapper.CommodityMapper;
import pers.xds.wtuapp.web.es.bean.EsCommodity;
import pers.xds.wtuapp.web.es.repository.CommodityRepository;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-09 18:01
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    /**
     * 每次分页显示的最大元素数量
     */
    private static final int MAX_PAGE_SIZE = 30;

    private CommodityMapper commodityMapper;

    private CommodityRepository commodityRepository;

    private OrderMapper orderMapper;

    private UserTradeMapper userTradeMapper;

    private TradeStatMapper tradeStatMapper;

    @Autowired
    public void setTradeStatMapper(TradeStatMapper tradeStatMapper) {
        this.tradeStatMapper = tradeStatMapper;
    }

    @Autowired
    public void setUserTradeMapper(UserTradeMapper userTradeMapper) {
        this.userTradeMapper = userTradeMapper;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setCommodityRepository(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    @Autowired
    public void setCommodityMapper(CommodityMapper commodityMapper) {
        this.commodityMapper = commodityMapper;
    }

    /**
     * 每个人最多可以发布的商品数量
     */
    public static final int MAX_ACTIVE_COMMODITY = 10;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCommodity(Commodity commodity) {
        Integer ownerId = commodity.getOwnerId();
        if (ownerId == null) {
            return -1;
        }
        Integer cnt = tradeStatMapper.selectSellingCount(ownerId);
        if (cnt == null) {
            TradeStat tradeStat = new TradeStat(ownerId);
            tradeStat.setSellingCount(1);
            tradeStatMapper.insert(tradeStat);
        } else if (cnt > MAX_ACTIVE_COMMODITY) {
            return -2;
        } else {
            tradeStatMapper.modifySellingCount(ownerId, cnt + 1);
        }
        // 使用自动生成的id
        commodity.setCommodityId(null);
        commodityMapper.insert(commodity);
        commodityRepository.save(new EsCommodity(
                commodity.getCommodityId(),
                commodity.getName(),
                System.currentTimeMillis(),
                commodity.getPrice(),
                commodity.getPreviewImage(),
                commodity.getTradeLocation()
        ));
        return commodity.getCommodityId();
    }

    @Nullable
    @Override
    public Commodity queryCommodity(int commodityId) {
        return commodityMapper.selectById(commodityId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean lockCommodity(int commodityId, int userId, String remark) {
        Commodity commodity = commodityMapper.selectById(commodityId);
        if (commodity == null) {
            return false;
        }
        if (commodity.getStatus() != Commodity.STATUS_ACTIVE) {
            return false;
        }
        Commodity updateCommodity = new Commodity();
        updateCommodity.setCommodityId(commodityId);
        updateCommodity.setStatus(Commodity.STATUS_TRADING);
        updateCommodity.setVersion(commodity.getVersion());
        if (commodityMapper.updateById(updateCommodity) == 1) {
            int ownerId = commodity.getOwnerId();
            // 添加交易记录
            Order order = new Order(commodityId, userId, remark, ownerId);
            orderMapper.insert(order);
            userTradeMapper.addUserTrade(order.getOrderId(), userId, ownerId);
            return true;
        }
        return false;
    }

    @Override
    public List<EsCommodity> searchCommodityByName(String commodityName, int page) {
        return searchCommodityByName(commodityName, MAX_PAGE_SIZE, page);
    }

    @Override
    public List<EsCommodity> searchCommodityByName(String commodityName, int page, int size) {
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        Page<EsCommodity> esCommodities = commodityRepository.searchByName(commodityName, Pageable.ofSize(size).withPage(page));
        return esCommodities.toList();
    }

    @Override
    public int querySellingCount(int uid) {
        Integer count = tradeStatMapper.selectSellingCount(uid);
        return count == null ? 0 : count;
    }


}
