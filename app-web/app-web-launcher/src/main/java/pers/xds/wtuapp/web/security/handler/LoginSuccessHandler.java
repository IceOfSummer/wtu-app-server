package pers.xds.wtuapp.web.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.security.SecurityJwtProvider;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.bean.UserAuth;
import pers.xds.wtuapp.web.security.common.AuthSuccessResponse;
import pers.xds.wtuapp.web.security.common.UserLoginPrincipal;
import pers.xds.wtuapp.web.service.UserAuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后的响应
 * <b>登录成功后返回用户相关信息</b>
 * @author DeSen Xu
 * @date 2022-09-01 12:01
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    private SecurityJwtProvider securityJwtProvider;

    private UserAuthService userAuthService;

    @Autowired
    public void setUserAuthService(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Autowired
    public void setSecurityJwtProvider(SecurityJwtProvider securityJwtProvider) {
        this.securityJwtProvider = securityJwtProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        UserLoginPrincipal principal = (UserLoginPrincipal) authentication.getPrincipal();

        UserAuth userAuth = userAuthService.queryNewestJwtId(principal.getUid());

        userAuthService.increaseJwtId(principal.getUid(), userAuth.getVersion());
        AuthSuccessResponse authSuccessResponse = new AuthSuccessResponse(
                securityJwtProvider.generateJwt(principal.getUid(), userAuth.getJwtId() + 1, principal.getAuthorities()),
                principal.getUid(),
                principal.getEmail(),
                principal.getNickname(),
                principal.getWtuId(),
                principal.getName(),
                principal.getClassName()
        );
        response.getWriter().write(ResponseTemplate.success(authSuccessResponse).toJson());
    }

}
