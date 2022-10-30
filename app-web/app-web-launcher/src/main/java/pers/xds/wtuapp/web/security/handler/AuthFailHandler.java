package pers.xds.wtuapp.web.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author DeSen Xu
 * @date 2022-09-01 12:32
 */
public class AuthFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(ResponseTemplate.fail(ResponseCode.WRONG_CREDENTIALS).toJson());
    }

}
