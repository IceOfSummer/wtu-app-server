package pers.xds.wtuapp.web.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 登录成功后的响应
 * <b>登录成功后返回用户id</b>
 * @author DeSen Xu
 * @date 2022-09-01 12:01
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = userService.queryUserDetail(principal.getId());
        Objects.requireNonNull(user).setUserId(principal.getId());
        response.getWriter().write(ResponseTemplate.success(user).toJson());
    }

}
