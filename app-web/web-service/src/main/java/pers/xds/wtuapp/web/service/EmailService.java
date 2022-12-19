package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.service.config.email.CommodityLockTemplateData;
import pers.xds.wtuapp.web.service.config.email.EmailBindTemplateData;

/**
 * @author DeSen Xu
 * @date 2022-12-04 22:30
 */
public interface EmailService {


    /**
     * 给卖家发送商品被购买的邮件
     * @param email 卖家邮件
     * @param data 邮件详细信息
     */
    void sendCommodityLockTip(String email, CommodityLockTemplateData data);

    /**
     * 发送邮箱验证码
     * @param email 要发送给谁
     * @param data 邮箱验证码信息
     */
    void sendEmailCaptcha(String email, EmailBindTemplateData data);

}
