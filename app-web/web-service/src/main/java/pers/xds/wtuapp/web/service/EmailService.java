package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.service.config.email.CommodityLockTemplateData;

/**
 * @author DeSen Xu
 * @date 2022-12-04 22:30
 */
public interface EmailService {


    /**
     * 给卖家发送商品被购买的邮件
     * @param uid 卖家邮件
     * @param email 卖家邮件
     * @param data 邮件详细信息
     */
    void sendCommodityLockTip(int uid, String email, CommodityLockTemplateData data);


}
