package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.service.bean.BaseWtuUserInfo;
import pers.xds.wtuapp.web.service.bean.WtuAuthInitParam;
import pers.xds.wtuapp.web.service.bean.WtuAuthParam;

import java.io.IOException;

/**
 * @author DeSen Xu
 * @date 2022-12-13 17:23
 */
public interface WtuAuthService {

    /**
     * 初始化登录url, GET请求
     */
    String LOGIN_URL = "https://auth.wtu.edu.cn/authserver/login?service=http%3A%2F%2Fjwglxt.wtu.edu.cn%2Fsso%2Fjziotlogin";

    /**
     * 验证码url, GET请求
     */
    String CAPTCHA_URL = "https://auth.wtu.edu.cn/authserver/captcha.html?ts=375";

    /**
     * 学生信息访问前缀，需要在最后加上学号来访问
     */
    String USER_INFO_URL_PREFIX = "http://jwglxt.wtu.edu.cn/xtgl/index_cxYhxxIndex.html?xt=jw&localeKey=zh_CN&_=1671201794224&gnmkdm=index&su=";

    /**
     * 登录成功需要拿取的Cookie名
     */
    String AUTH_COOKIE_NAME = "CASTGC";


    /**
     * 初始化登录，返回图像验证码
     * @return 图片验证码<p>
     *     - {@link ServiceCode#UNKNOWN_ERROR} 表示发生未知错误，请稍后再试
     * @throws IOException 连接教务系统失败
     */
    ServiceCodeWrapper<WtuAuthInitParam> initAuth() throws IOException;


    /**
     * 登录认证
     * @param wtuAuthParam 登录参数
     * @return 服务码<p>
     *     - {@link ServiceCode#BAD_REQUEST} 用户名或密码或验证码错误
     *     - {@link ServiceCode#UNKNOWN_ERROR} 发生未知错误
     * @throws IOException 连接教务系统失败
     */
    ServiceCodeWrapper<BaseWtuUserInfo> auth(WtuAuthParam wtuAuthParam) throws IOException;
}
