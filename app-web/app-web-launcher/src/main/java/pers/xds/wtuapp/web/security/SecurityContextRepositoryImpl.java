package pers.xds.wtuapp.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import pers.xds.wtuapp.security.token.ExpiredAuthenticationToken;
import pers.xds.wtuapp.security.token.JwtAuthenticationToken;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.SecurityJwtProvider;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.security.bean.JwtParseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author DeSen Xu
 * @date 2022-12-31 20:53
 */
@Configuration
public class SecurityContextRepositoryImpl implements SecurityContextRepository {

    private SecurityJwtProvider securityJwtProvider;

    @Autowired
    public void setSecurityJwtProvider(SecurityJwtProvider securityJwtProvider) {
        this.securityJwtProvider = securityJwtProvider;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        String jwt = request.getHeader(SecurityConstant.HEADER_AUTHORIZATION);
        if (jwt == null || jwt.isEmpty()) {
            return context;
        }
        JwtParseResult jwtParseResult = securityJwtProvider.parseJwt(jwt);
        if (jwtParseResult == null) {
            return context;
        }
        if (jwtParseResult.isExpired()) {
            context.setAuthentication(new ExpiredAuthenticationToken(jwtParseResult));
            return context;
        }
        UserPrincipal userPrincipal = jwtParseResult.getPrincipal();
        JwtAuthenticationToken token = new JwtAuthenticationToken(userPrincipal, userPrincipal.getAuthorities());
        token.setAuthenticated(true);
        context.setAuthentication(token);
        return context;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        // jwt不需要保存
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String jwt = request.getHeader(SecurityConstant.HEADER_AUTHORIZATION);
        return jwt != null && !jwt.isEmpty();
    }
}
