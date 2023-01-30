package pers.xds.wtuapp.web.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.service.UserAuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author DeSen Xu
 * @date 2022-09-01 12:32
 */
public class AuthFailHandler implements AuthenticationFailureHandler {

    private final UserAuthService userAuthService;

    public AuthFailHandler(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        userAuthService.increaseLoginFailCount(request.getParameter(SecurityConstant.LOGIN_USERNAME_PARAMETER_NAME));
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(ResponseTemplate.fail(ResponseCode.WRONG_CREDENTIALS).toJson());
    }

}
