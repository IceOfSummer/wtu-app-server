package pers.xds.wtuapp.web.service.bean;

import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2022-12-14 14:38
 */
public class WtuAuthInitParam {

    public String salt;

    public String lt;

    public Map<String, String> cookies;

    /**
     * base64格式的图片验证码
     */
    public String captcha;

    public WtuAuthInitParam(String salt, String lt, Map<String, String> cookies, String captcha) {
        this.salt = salt;
        this.lt = lt;
        this.cookies = cookies;
        this.captcha = captcha;
    }
}
