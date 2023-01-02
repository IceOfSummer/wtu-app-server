package pers.xds.wtuapp.security;


/**
 * 有关Spring Security的常量<p/>
 * 以EL开头的用于 {@link org.springframework.security.access.prepost.PreAuthorize} 注解, 来完成身份验证
 * @author DeSen Xu
 * @date 2022-08-31 0:01
 */
public interface SecurityConstant {

    /**
     * 普通用户
     */
    String ROLE_NORMAL_USER = "USER";

    /**
     * 已经登录的用户
     */
    String EL_AUTHENTICATED = "isAuthenticated()";

    /**
     * 允许所有用户访问
     */
    String EL_PERMIT_ALL = "permitAll";

    /**
     * 身份验证的请求头名称
     */
    String HEADER_AUTHORIZATION = "Authorization";

    /**
     * 用于jwt续签的响应体，该响应头存储新的token
     */
    String HEADER_RENEW_TOKEN = "Token";
}
