package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.mapper.OrderMapper;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCommodity(Commodity commodity) {
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


}
