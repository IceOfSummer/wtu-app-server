package pers.xds.wtuapp.web.service.impl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.ServiceCodeWrapper;
import pers.xds.wtuapp.web.service.WtuAuthService;
import pers.xds.wtuapp.web.service.bean.BaseWtuUserInfo;
import pers.xds.wtuapp.web.service.bean.WtuAuthInitParam;
import pers.xds.wtuapp.web.service.bean.WtuAuthParam;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * @author DeSen Xu
 * @date 2022-12-14 14:32
 */
@Service
public class WtuAuthServiceImpl implements WtuAuthService {

    @Override
    public ServiceCodeWrapper<WtuAuthInitParam> initAuth() throws IOException {
        Connection connect = Jsoup.connect(LOGIN_URL);
        Document document = connect.get();
        Element body = document.body();
        Element saltElement = body.getElementById("pwdDefaultEncryptSalt");
        if (saltElement == null) {
            return ServiceCodeWrapper.fail(ServiceCode.UNKNOWN_ERROR);
        }
        String salt = Objects.requireNonNull(saltElement).val();

        Elements res = body.getElementsByAttributeValue("name", "lt");
        Element ltElement = res.first();
        if (ltElement == null) {
            return ServiceCodeWrapper.fail(ServiceCode.UNKNOWN_ERROR);
        }
        String lt = ltElement.val();
        Map<String, String> cookies = connect.response().cookies();
        // 获取验证码
        Connection captchaConnection = Jsoup.connect(CAPTCHA_URL);
        captchaConnection.cookies(cookies);
        captchaConnection.ignoreContentType(true);
        Connection.Response captchaResponse = captchaConnection.execute();
        String captcha = Base64.getEncoder().encodeToString(captchaResponse.bodyAsBytes());
        WtuAuthInitParam wtuAuthInitParam = new WtuAuthInitParam(salt, lt, cookies, captcha);
        return new ServiceCodeWrapper<>(ServiceCode.SUCCESS, wtuAuthInitParam);
    }

    @Override
    public ServiceCodeWrapper<BaseWtuUserInfo> auth(WtuAuthParam wtuAuthParam) throws IOException {
        Connection connect = Jsoup.connect(LOGIN_URL);
        connect.data("username", wtuAuthParam.username);
        connect.data("password", wtuAuthParam.password);
        connect.data("captchaResponse", wtuAuthParam.captcha);
        connect.data("rememberMe", "on");
        connect.data("lt", wtuAuthParam.lt);
        connect.data("dllt", "userNamePasswordLogin");
        connect.data("execution", "e1s1");
        connect.data("_eventId", "submit");
        connect.data("rmShown", "1");
        connect.cookies(wtuAuthParam.cookies);
        connect.followRedirects(true);

        connect.post();
        Map<String, String> cookies = connect.response().cookies();
        String cookie = cookies.get(AUTH_COOKIE_NAME);
        if (cookie == null) {
            return ServiceCodeWrapper.fail(ServiceCode.BAD_REQUEST);
        }
        // 爬取姓名和班级
        BaseWtuUserInfo baseWtuUserInfo = fetchWtuUserInfo(wtuAuthParam.username, cookies);
        if (baseWtuUserInfo == null) {
            return ServiceCodeWrapper.fail(ServiceCode.UNKNOWN_ERROR);
        }
        return ServiceCodeWrapper.success(baseWtuUserInfo);
    }

    private BaseWtuUserInfo fetchWtuUserInfo(String username, Map<String, String> cookies) throws IOException {
        Connection edu = Jsoup.connect(USER_INFO_URL_PREFIX + username);
        edu.followRedirects(true);
        edu.cookies(cookies);
        Document document = edu.get();
        Elements media = document.getElementsByClass("media-body");
        Element first = media.first();
        if (first == null) {
            return null;
        }
        Element name = first.getElementsByTag("h4").first();
        if (name == null) {
            return null;
        }
        Element className = first.getElementsByTag("p").first();
        if (className == null) {
            return null;
        }
        return new BaseWtuUserInfo(name.text(), className.text());
    }
}
