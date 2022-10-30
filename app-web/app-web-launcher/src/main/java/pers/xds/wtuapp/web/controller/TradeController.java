package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.service.TradingRecordService;
import pers.xds.wtuapp.web.service.bean.TradingInfo;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;

import java.util.List;

/**
 * 用于管理交易的接口
 * @author DeSen Xu
 * @date 2022-09-09 18:49
 */
@RestController
@RequestMapping("trade")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class TradeController {

    private TradingRecordService tradingRecordService;

    @Autowired
    public void setTradingRecordService(TradingRecordService tradingRecordService) {
        this.tradingRecordService = tradingRecordService;
    }

    @GetMapping("/trading/record")
    public ResponseTemplate<List<TradingInfo>> getUserTradingRecord(
            @RequestParam(required = false, defaultValue = "0", value = "p") int page,
            @RequestParam(required = false, defaultValue = "5", value = "s") int size) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(tradingRecordService.getTradingInfo(userPrincipal.getId(), page, size));
    }



}
