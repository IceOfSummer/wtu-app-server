package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.OrderService;
import pers.xds.wtuapp.web.database.view.OrderDetail;
import pers.xds.wtuapp.web.service.ServiceCode;

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
    @GetMapping("all")
    public ResponseTemplate<List<OrderDetail>> getUserTradingRecord(
            @RequestParam(value = "p", required = false, defaultValue = "0") int page,
            @RequestParam(value = "s", required = false, defaultValue = "5") int size
    ) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(orderService.getUserOrderDetails(userPrincipal.getId(), page, size));
    }


    /**
     * 查询用户向外的出售记录
     * @return 用户出售的商品
     */
    @GetMapping("/sold")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<List<OrderDetail>> queryUserSoldRecordSimply(@RequestParam(value = "i") int id,
                                                                          @RequestParam(value = "p", required = false, defaultValue = "0") int page,
                                                                          @RequestParam(value = "s", required = false, defaultValue = "5") int size) {
        return ResponseTemplate.success(orderService.getUserSoldOrder(id, page, size));
    }


    /**
     * 获取用户待收货商品
     * @return 待收货商品
     */
    @GetMapping("/pending/receive")
    public ResponseTemplate<List<OrderDetail>> queryPendingReceiveOrder() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(orderService.getUserPendingReceiveOrder(userPrincipal.getId()));
    }


    /**
     * 获取用户待发货商品
     * @return 待发货商品
     */
    @GetMapping("/pending/delivery")
    public ResponseTemplate<List<OrderDetail>> queryPendingDeliveryOrder() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(orderService.getUserPendingDeliveryOrder(userPrincipal.getId()));
    }

    /**
     * 买家标记商品已经收到
     */
    @PostMapping("/{orderId}/done")
    public ResponseTemplate<Void> markReceived(@PathVariable int orderId,
                                               @RequestParam(value = "s") int sellerId,
                                               @RequestParam(value = "r", required = false) String remark) {
        final int maxLen = 200;
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        if (remark != null && remark.length() >= maxLen) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        ServiceCode code = orderService.markTradeDone(userPrincipal.getId(), sellerId, orderId, remark);
        if (code == ServiceCode.SUCCESS) {
            return ResponseTemplate.success();
        }
        return ResponseTemplate.fail(ResponseCode.ELEMENT_NOT_EXIST);
    }

    /**
     * 买家或卖家要求取消订单
     */
    @PostMapping("/{orderId}/cancel")
    public ResponseTemplate<Void> cancelOrder(@PathVariable int orderId,
                                              @RequestParam(value = "r", required = false) String remark) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        remark = remark.isEmpty() ? null : remark;
        ServiceCode code = orderService.markTradeFail(userPrincipal.getId(), orderId, remark);
        if (code == ServiceCode.SUCCESS) {
            return ResponseTemplate.success();
        } else if (code == ServiceCode.CONCURRENT_ERROR) {
            return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE, "服务器繁忙，请稍后再试");
        }
        // code == ServiceCode#NOT_EXIST
        return ResponseTemplate.fail(ResponseCode.ELEMENT_NOT_EXIST);
    }

}
