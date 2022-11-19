package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.database.bean.UserTrade;
import pers.xds.wtuapp.web.database.mapper.OrderDetailMapper;
import pers.xds.wtuapp.web.service.OrderService;
import pers.xds.wtuapp.web.database.view.OrderDetail;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-03 16:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    private OrderDetailMapper orderDetailMapper;

    @Autowired
    public void setOrderDetailMapper(OrderDetailMapper orderDetailMapper) {
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    public List<OrderDetail> getUserOrderDetails(int userid, int page, int size) {
        return orderDetailMapper.selectOrders(userid, null, new Page<>(page, size)).getRecords();
    }

    @Override
    public List<OrderDetail> getUserActiveOrderDetails(int uid) {
        return orderDetailMapper.selectOrders(uid, UserTrade.STATUS_TRADING, new Page<>(0, 50L)).getRecords();
    }



}
