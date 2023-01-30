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
     * 若要插入数据库，请使用{@link Roles#ROLE_NORMAL_USER}
     */
    String ROLE_NORMAL_USER = "USER";

    /**
     * 管理员
     * 若要插入数据库，请使用{@link Roles#ROLE_ADMIN}
     */
    String ROLE_ADMIN = "ADMIN";

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
     * 使用表单登录时，用户名的名称
     */
    String LOGIN_USERNAME_PARAMETER_NAME = "username";

    /**
     * 使用表单登录时，密码的名称
     */
    String LOGIN_PASSWORD_PARAMETER_NAME = "password";

    /**
     * 登录地址
     */
    String LOGIN_PATH = "/login";

    /**
     * 只允许管理员访问
     */
    String EL_ADMIN_ONLY = "hasRole('" + ROLE_ADMIN + "')";

}
