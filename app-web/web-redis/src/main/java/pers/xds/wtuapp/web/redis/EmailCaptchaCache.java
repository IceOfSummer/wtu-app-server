package pers.xds.wtuapp.web.redis;

import pers.xds.wtuapp.web.redis.bean.EmailData;

/**
 * @author DeSen Xu
 * @date 2022-12-19 15:52
 */
public interface EmailCaptchaCache {


    /**
     * 保存用户验证码, (半小时后过期)
     * @param uid 用户id
     * @param email 邮箱
     * @param captcha 验证码
     */
    void saveEmailCaptcha(int uid, String email, String captcha);


    /**
     * 获取保存的邮箱验证码
     * @param uid 用户id
     * @return 保存的邮箱验证码
     */
    EmailData getEmailCaptcha(int uid);
}
