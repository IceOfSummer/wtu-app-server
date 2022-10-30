package pers.xds.wtuapp.web.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后的响应
 * <b>登录成功后返回用户id</b>
 * @author DeSen Xu
 * @date 2022-09-01 12:01
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        response.getWriter().write(ResponseTemplate.success(principal.getId()).toJson());
    }

}
