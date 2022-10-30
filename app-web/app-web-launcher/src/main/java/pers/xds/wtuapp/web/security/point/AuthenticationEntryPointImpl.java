package pers.xds.wtuapp.web.security.point;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义403响应
 * @author DeSen Xu
 * @date 2022-08-31 17:45
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(ResponseTemplate.fail(ResponseCode.FORBIDDEN).toJson());
    }

}
