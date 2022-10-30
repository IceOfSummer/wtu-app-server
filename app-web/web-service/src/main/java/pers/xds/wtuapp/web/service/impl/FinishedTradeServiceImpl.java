package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.service.FinishedTradeService;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.mapper.FinishedTradeMapper;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-17 21:45
 */
@Service
public class FinishedTradeServiceImpl implements FinishedTradeService {

    private FinishedTradeMapper finishedTradeMapper;

    @Autowired
    public void setFinishedTradeMapper(FinishedTradeMapper finishedTradeMapper) {
        this.finishedTradeMapper = finishedTradeMapper;
    }

    @Override
    public List<Order> getSoldOrder(int userid, int page, int size) {
        Page<Order> orderPage = new Page<>(page, size);
        return finishedTradeMapper.getSoldOrder(orderPage, userid).getRecords();
    }

    @Override
    public List<Order> getSoldOrderSimply(int userid, int page, int size) {
        Page<Order> orderPage = new Page<>(page, size);
        return finishedTradeMapper.getSoldOrderSimply(orderPage, userid).getRecords();
    }
}
