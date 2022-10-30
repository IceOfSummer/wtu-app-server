package pers.xds.wtuapp.web.service;

import org.springframework.lang.NonNull;
import pers.xds.wtuapp.web.database.bean.TradeStat;

/**
 * @author DeSen Xu
 * @date 2022-09-25 16:37
 */
public interface TradeStatService {

    /**
     * 获取交易统计
     * @param userId 用户id
     * @return 交易统计
     */
    @NonNull
    TradeStat getTradeStat(int userId);


}
