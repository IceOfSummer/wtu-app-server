package pers.xds.wtuapp.web.security.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.service.UserAuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 账号保护过滤器。
 * <p>
 * 当用户登录失败超过一定次数后，拦截并阻止该账户的登录。
 * 应当声明在{@link UsernamePasswordAuthenticationFilter}之前
 * <p>
 * @see pers.xds.wtuapp.web.security.handler.AuthFailHandler AuthFailHandler: 当用户登录失败时，在这里进行记录
 * @author DeSen Xu
 * @date 2023-01-30 14:25
 */
@Component
public class AccountProtectFilter extends OncePerRequestFilter {

    private final UserAuthService userAuthService;

    /**
     * 当一段时间内登录失败次数超过该值，则临时冻结该用户
     */
    private static final int MAX_LOCK_ACCOUNT_TIMES = 6;


    public AccountProtectFilter(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (SecurityConstant.LOGIN_PATH.equals(request.getServletPath()) && RequestMethod.POST.name().equals(request.getMethod())) {
            String username = request.getParameter(SecurityConstant.LOGIN_USERNAME_PARAMETER_NAME);
            int count = userAuthService.queryLoginFailCount(username);
            if (count >= MAX_LOCK_ACCOUNT_TIMES) {
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().write(ResponseTemplate.fail(ResponseCode.ACCOUNT_FREEZE_TEMPORARILY).toJson());
                return;
            }
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
