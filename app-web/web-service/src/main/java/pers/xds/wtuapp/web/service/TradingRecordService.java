package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.service.bean.TradingInfo;
import pers.xds.wtuapp.web.database.bean.TradingRecord;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-29 23:17
 */
public interface TradingRecordService {

    /**
     * 获取用户正在交易的商品记录
     * @param userid 用户id
     * @param page 第几页
     * @param size 每页最多显示多少行
     * @return 正在交易的商品记录
     */
    List<TradingRecord> getUserTradingRecord(int userid, int page, int size);

    /**
     * 获取用户正在交易的商品记录
     * @param userid 用户id
     * @param page 第几页
     * @param size 每页最多显示多少行
     * @return 正在交易的商品记录
     */
    List<TradingInfo> getTradingInfo(int userid, int page, int size);


}
