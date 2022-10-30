package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.service.CommodityService;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.bean.TradingRecord;
import pers.xds.wtuapp.web.database.mapper.CommodityMapper;
import pers.xds.wtuapp.web.database.mapper.TradingRecordMapper;
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

    private TradingRecordMapper tradingRecordMapper;

    @Autowired
    public void setTradingRecordMapper(TradingRecordMapper tradingRecordMapper) {
        this.tradingRecordMapper = tradingRecordMapper;
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
        Commodity com = commodityMapper.selectById(commodityId);
        if (com == null) {
            return false;
        }
        if (com.getStatus() != Commodity.STATUS_ACTIVE) {
            return false;
        }
        Commodity commodity = new Commodity();
        commodity.setCommodityId(commodityId);
        commodity.setStatus(Commodity.STATUS_TRADING);
        commodity.setVersion(com.getVersion());
        if (commodityMapper.updateById(commodity) == 1) {
            // 添加交易记录
            tradingRecordMapper.insert(new TradingRecord(commodityId, userId, remark));
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
