package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.service.FinishedTradeService;
import pers.xds.wtuapp.web.common.ResponseTemplate;

import java.util.List;

/**
 * 订单查询接口
 * @author DeSen Xu
 * @date 2022-09-17 19:00
 */
@RestController
@RequestMapping("record")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class TradeRecordController {

    private FinishedTradeService finishedTradeService;

    @Autowired
    public void setFinishedTradeService(FinishedTradeService finishedTradeService) {
        this.finishedTradeService = finishedTradeService;
    }

    /**
     * 查询用户向外交易完成的简要记录
     * @return 用户出售的商品
     */
    @GetMapping("/{id}/sold")
    public ResponseTemplate<List<Order>> queryUserSoldRecordSimply(@PathVariable int id,
                                                                   @RequestParam(value = "p", required = false, defaultValue = "0") int page,
                                                                   @RequestParam(value = "s", required = false, defaultValue = "5") int size) {
        return ResponseTemplate.success(finishedTradeService.getSoldOrderSimply(id, page, size));
    }



}
