package pers.xds.wtuapp.web.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * @author DeSen Xu
 * @date 2022-09-09 18:07
 */
public class SecurityContextUtil {

    private SecurityContextUtil() {}

    /**
     * 获取用户的身份凭据
     * @return 用户的身份凭据，<b>若用户未登录，返回Null</b>
     */
    public static UserPrincipal getUserPrincipal() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取验证信息
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
