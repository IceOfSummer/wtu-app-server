package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
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
    @NonNull
    public TradeStat getTradeStat(int userId) {
        TradeStat tradeStat = tradeStatMapper.selectUserTradeState(userId);
        if (tradeStat == null) {
            // 插入
            tradeStatMapper.insertWithSellingCountOnly(userId, 0);
            tradeStat = new TradeStat();
            tradeStat.setSellingCount(0);
            tradeStat.setDeliveryCount(0);
        }
        return tradeStat;
    }

}
