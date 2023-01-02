package pers.xds.wtuapp.web.security.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.SecurityJwtProvider;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.security.bean.JwtParseResult;
import pers.xds.wtuapp.security.token.ExpiredAuthenticationToken;
import pers.xds.wtuapp.web.database.bean.UserAuth;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.UserAuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt续签过滤器
 * @author DeSen Xu
 * @date 2023-01-01 20:43
 */
@Component
public class JwtRenewFilter extends OncePerRequestFilter {

    private UserAuthService userAuthService;

    private SecurityJwtProvider securityJwtProvider;

    @Autowired
    public void setSecurityJwtProvider(SecurityJwtProvider securityJwtProvider) {
        this.securityJwtProvider = securityJwtProvider;
    }

    @Autowired
    public void setUserAuthService(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextUtil.getAuthentication();
        if (authentication instanceof ExpiredAuthenticationToken) {
            // 尝试续签
            ExpiredAuthenticationToken token = (ExpiredAuthenticationToken) authentication;
            JwtParseResult jwtParseResult = token.getJwtParseResult();
            UserPrincipal userPrincipal = token.getPrincipal();
            int uid = userPrincipal.getId();
            int jwtId = jwtParseResult.getJwtId();
            UserAuth userAuth = userAuthService.queryNewestJwtId(uid);

            if (jwtId == userAuth.getJwtId()) {
                // 续签
                userAuthService.increaseJwtId(uid, userAuth.getVersion());
                String jwt = securityJwtProvider.generateJwt(userPrincipal, jwtId + 1);
                response.addHeader("Access-Control-Expose-Headers", SecurityConstant.HEADER_RENEW_TOKEN);
                response.addHeader(SecurityConstant.HEADER_RENEW_TOKEN, jwt);
                authentication.setAuthenticated(true);
            }
        }
        filterChain.doFilter(request, response);
    }
}
