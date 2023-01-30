package pers.xds.wtuapp.web.security.common;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import pers.xds.wtuapp.web.service.UserAuthService;

/**
 * 自定义的认证提供。
 * <p>
 * @author DeSen Xu
 * @date 2023-01-30 15:43
 */
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserAuthService userAuthService;

    /**
     * 当一段时间内登录失败次数超过该值，则临时冻结该用户
     */
    private static final int MAX_LOCK_ACCOUNT_TIMES = 1;

    public CustomAuthenticationProvider(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return super.authenticate(authentication);
    }
}
