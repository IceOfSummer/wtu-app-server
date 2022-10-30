package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.database.bean.TradeStat;
import pers.xds.wtuapp.web.service.TradeStatService;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;

/**
 * @author DeSen Xu
 * @date 2022-09-25 16:41
 */
@RestController
@RequestMapping("stat")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class TradeStatController {

    private TradeStatService tradeStatService;

    @Autowired
    public void setTradeStatService(TradeStatService tradeStatService) {
        this.tradeStatService = tradeStatService;
    }

    /**
     * 查询自己的交易统计
     * @return 交易统计
     */
    @GetMapping("info")
    public ResponseTemplate<TradeStat> getTradeStat() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        TradeStat tradeStat = tradeStatService.getTradeStat(userPrincipal.getId());
        return ResponseTemplate.success(tradeStat);
    }

}
