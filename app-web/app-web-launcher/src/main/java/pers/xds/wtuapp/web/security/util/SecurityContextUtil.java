package pers.xds.wtuapp.web.security.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
     * 获取当前请求的sessionId
     * @return sessionId, 理论上永不为空，除非在非请求线程中调用
     */
    public static String getSessionId() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return servletRequestAttributes == null ? null : servletRequestAttributes.getSessionId();
    }
}
