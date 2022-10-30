package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.service.TradingRecordService;
import pers.xds.wtuapp.web.service.bean.TradingInfo;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.bean.TradingRecord;
import pers.xds.wtuapp.web.database.mapper.CommodityMapper;
import pers.xds.wtuapp.web.database.mapper.TradingRecordMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-29 23:18
 */
@Service
public class TradingRecordServiceImpl implements TradingRecordService {

    private TradingRecordMapper tradingRecordMapper;

    private CommodityMapper commodityMapper;

    @Autowired
    public void setCommodityMapper(CommodityMapper commodityMapper) {
        this.commodityMapper = commodityMapper;
    }

    @Autowired
    public void setTradingRecordMapper(TradingRecordMapper tradingRecordMapper) {
        this.tradingRecordMapper = tradingRecordMapper;
    }

    @Override
    public List<TradingRecord> getUserTradingRecord(int userid, int page, int size) {
        return tradingRecordMapper.getTradingRecordByCustomerId(new Page<>(page, size), userid).getRecords();
    }

    @Override
    public List<TradingInfo> getTradingInfo(int userid, int page, int size) {
        List<TradingRecord> userTradingRecord = getUserTradingRecord(userid, page, size);
        int len = userTradingRecord.size();
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        ArrayList<Integer> ids = new ArrayList<>(len);
        for (TradingRecord tradingRecord : userTradingRecord) {
            ids.add(tradingRecord.getCommodityId());
        }
        wrapper.select(TradingInfo.REQUIRED_COMMODITY_COLUMN).in(CommodityMapper.COLUMN_COMMODITY_ID, ids);
        List<Commodity> commodities = commodityMapper.selectList(wrapper);

        ArrayList<TradingInfo> tradingInfos = new ArrayList<>(len);

        Iterator<Commodity> it1 = commodities.iterator();
        Iterator<TradingRecord> it2 = userTradingRecord.iterator();
        // it1.len == it2.len
        while (it1.hasNext()) {
            tradingInfos.add(new TradingInfo(it1.next(), it2.next()));
        }
        return tradingInfos;
    }


}
