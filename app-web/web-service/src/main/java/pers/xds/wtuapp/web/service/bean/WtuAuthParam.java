package pers.xds.wtuapp.web.service.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2022-12-14 14:56
 */
public class WtuAuthParam {

    public String username;

    public String password;

    public String captcha;

    public String lt;

    public Map<String, String> cookies;

    public static final String COOKIE_JSESSION_ID_AUTH = "JSESSIONID_auth";

    public static final String COOKIE_ROUTE = "route";

    public WtuAuthParam() {
    }

    public WtuAuthParam(String username, String password, String captcha, String lt, String jsessionId, String route) {
        this.username = username;
        this.password = password;
        this.captcha = captcha;
        this.lt = lt;
        this.cookies = new HashMap<>(2);
        cookies.put(COOKIE_JSESSION_ID_AUTH, jsessionId);
        cookies.put(COOKIE_ROUTE, route);
    }


}
