package pers.xds.wtuapp.web.service.impl;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.database.bean.FinishedTrade;
import pers.xds.wtuapp.web.database.mapper.FinishedTradeMapper;
import pers.xds.wtuapp.web.service.FinishedTradeService;

/**
 * @author DeSen Xu
 * @date 2022-11-30 23:02
 */
@Service
public class FinishedTradeServiceImpl implements FinishedTradeService {

    private FinishedTradeMapper finishedTradeMapper;

    @Autowired
    public void setFinishedTradeMapper(FinishedTradeMapper finishedTradeMapper) {
        this.finishedTradeMapper = finishedTradeMapper;
    }
    @Nullable
    @Override
    public FinishedTrade queryFinishedTrade(int orderId) {
        return finishedTradeMapper.selectById(orderId);
    }
}
