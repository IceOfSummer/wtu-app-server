package pers.xds.wtuapp.web.controller;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.errorcode.ResponseWrapper;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.view.OrderDetail;
import pers.xds.wtuapp.web.database.view.OrderPreview;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.OrderService;
import pers.xds.wtuapp.web.service.bean.UpdateOrderStatusParam;
import pers.xds.wtuapp.web.util.StringUtils;

import java.util.List;

/**
 * 订单管理
 *
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
     *
     * @return 当前正在交易的订单
     */
    @GetMapping("all")
    public ResponseTemplate<List<OrderPreview>> getUserTradingRecord(
            @RequestParam(value = "p", required = false, defaultValue = "0") int page,
            @RequestParam(value = "s", required = false, defaultValue = "5") int size
    ) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(orderService.getUserOrderDetails(userPrincipal.getId(), page, size));
    }


    /**
     * 查询用户向外的出售记录
     *
     * @return 用户出售的商品
     */
    @GetMapping("/sold")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<List<OrderPreview>> queryUserSoldRecordSimply(@RequestParam(value = "i") int id,
                                                                          @RequestParam(value = "p", required = false, defaultValue = "0") int page,
                                                                          @RequestParam(value = "s", required = false, defaultValue = "5") int size) {
        return ResponseTemplate.success(orderService.getUserSoldOrder(id, page, size));
    }


    /**
     * 获取用户待收货商品
     *
     * @return 待收货商品
     */
    @GetMapping("/pending/receive")
    public ResponseTemplate<List<OrderPreview>> queryPendingReceiveOrder(@RequestParam(value = "p", required = false, defaultValue = "0") int page,
                                                                         @RequestParam(value = "s", required = false, defaultValue = "5") int size) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(orderService.getUserPendingReceiveOrder(userPrincipal.getId(), page, size));
    }


    /**
     * 获取用户待发货商品
     *
     * @return 待发货商品
     */
    @GetMapping("/pending/delivery")
    public ResponseTemplate<List<OrderPreview>> queryPendingDeliveryOrder(@RequestParam(value = "p", required = false, defaultValue = "0") int page,
                                                                          @RequestParam(value = "s", required = false, defaultValue = "5") int size) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(orderService.getUserPendingDeliveryOrder(userPrincipal.getId(), page, size));
    }

    /**
     * 买家标记商品已经收到
     */
    @PostMapping("/{orderId}/done")
    public ResponseWrapper<Void> markReceived(@PathVariable int orderId,
                                              @RequestParam(value = "s") String isSeller,
                                              @RequestParam(value = "ps") int previousStatus,
                                              @RequestParam(value = "r", required = false) String remark) {
        UpdateOrderStatusParam updateOrderStatusParam = getStatusParam(orderId, isSeller, previousStatus, remark);
        orderService.markTradeDone(updateOrderStatusParam);
        return ResponseWrapper.success();
    }

    /**
     * 买家或卖家要求取消订单
     */
    @PostMapping("/{orderId}/cancel")
    public ResponseWrapper<Void> cancelOrder(@PathVariable int orderId,
                                              @RequestParam(value = "s") String isSeller,
                                              @RequestParam(value = "ps") int previousStatus,
                                              @RequestParam(value = "r", required = false) String remark) {
        UpdateOrderStatusParam updateOrderStatusParam = getStatusParam(orderId, isSeller, previousStatus, remark);
        orderService.markTradeFail(updateOrderStatusParam);
        return ResponseWrapper.success();
    }

    @Nullable
    private UpdateOrderStatusParam getStatusParam(int orderId, String isSeller, int previousStatus, String remark) {
        final int maxLen = 80;
        if (remark != null && remark.length() >= maxLen) {
            return null;
        }
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return new UpdateOrderStatusParam(userPrincipal.getId(), orderId, StringUtils.isTrue(isSeller), previousStatus, remark);
    }

    /**
     * 查询商品详细信息
     */
    @GetMapping("/{orderId}/detail")
    public ResponseTemplate<OrderDetail> queryOrderDetail(@PathVariable int orderId) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(orderService.queryOrderDetail(userPrincipal.getId(), orderId));
    }

}
