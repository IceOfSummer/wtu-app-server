package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.OrderService;
import pers.xds.wtuapp.web.service.bean.OrderDetail;

import java.util.List;

/**
 * 订单管理
 * @author DeSen Xu
 * @date 2022-11-04 23:04
 */
@RestController
@RequestMapping("order")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 获取当前正在交易的订单
     * @return 当前正在交易的订单
     */
    @GetMapping("active")
    public ResponseTemplate<List<OrderDetail>> getUserTradingRecord() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(orderService.getUserActiveOrderDetails(userPrincipal.getId()));
    }


    /**
     * 查询用户向外交易完成的简要记录
     * @return 用户出售的商品
     */
    @GetMapping("/{id}/sold")
    public ResponseTemplate<List<OrderDetail>> queryUserSoldRecordSimply(@PathVariable int id,
                                                                          @RequestParam(value = "p", required = false, defaultValue = "0") int page,
                                                                          @RequestParam(value = "s", required = false, defaultValue = "5") int size) {
        return ResponseTemplate.success(orderService.getUserOrderDetails(id, page, size));
    }

}
