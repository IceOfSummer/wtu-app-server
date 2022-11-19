package pers.xds.wtuapp.web.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.xds.wtuapp.web.service.OrderService;


@SpringBootTest
class OrderServiceImplTest {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    void getUserOrderDetails() {
        System.out.println(orderService.getUserOrderDetails(1, 0, 10));
    }

    @Test
    void getUserActiveOrderDetails() {
        System.out.println(orderService.getUserActiveOrderDetails(1));
    }
}