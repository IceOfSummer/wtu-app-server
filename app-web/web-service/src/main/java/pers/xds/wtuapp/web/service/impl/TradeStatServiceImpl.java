package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.service.TradeStatService;
import pers.xds.wtuapp.web.database.bean.TradeStat;
import pers.xds.wtuapp.web.database.mapper.TradeStatMapper;

/**
 * @author DeSen Xu
 * @date 2022-09-25 16:38
 */
@Service
public class TradeStatServiceImpl implements TradeStatService {

    private TradeStatMapper tradeStatMapper;

    @Autowired
    public void setTradeStatMapper(TradeStatMapper tradeStatMapper) {
        this.tradeStatMapper = tradeStatMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @NonNull
    public TradeStat getTradeStat(int userId) {
        TradeStat tradeStat = tradeStatMapper.selectById(userId);
        if (tradeStat == null) {
            // 插入
            TradeStat stat = new TradeStat();
            stat.setUserId(userId);
            tradeStatMapper.insert(stat);
            return stat;
        }
        return tradeStat;
    }

}
