package pers.xds.wtuapp.web.service;

import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.database.bean.FinishedTrade;

/**
 * @author DeSen Xu
 * @date 2022-11-30 23:02
 */
public interface FinishedTradeService {

    /**
     * 查询已经完成的订单
     * @param orderId 订单id
     * @return 订单详细，返回null表示不存在
     */
    @Nullable
    FinishedTrade queryFinishedTrade(int orderId);


}
