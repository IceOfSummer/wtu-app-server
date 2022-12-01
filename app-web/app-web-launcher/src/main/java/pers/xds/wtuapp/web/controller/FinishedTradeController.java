package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.bean.FinishedTrade;
import pers.xds.wtuapp.web.service.FinishedTradeService;

/**
 * @author DeSen Xu
 * @date 2022-11-30 23:00
 */
@RestController
@RequestMapping("trade")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class FinishedTradeController {
    
    private FinishedTradeService finishedTradeService;
            
    @Autowired
    public void setFinishedTradeService(FinishedTradeService finishedTradeService) {
        this.finishedTradeService = finishedTradeService;
    }
    
    @GetMapping("{orderId}")
    public ResponseTemplate<FinishedTrade> getFinishedTrade(@PathVariable int orderId) {
        return ResponseTemplate.success(finishedTradeService.queryFinishedTrade(orderId));
    }
    
    
}
