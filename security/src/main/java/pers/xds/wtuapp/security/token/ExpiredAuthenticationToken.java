package pers.xds.wtuapp.security.token;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pers.xds.wtuapp.security.bean.JwtParseResult;

import java.util.Collection;
import java.util.Collections;

/**
 * 使用了过期的jwt令牌
 * @author DeSen Xu
 * @date 2023-01-01 15:23
 */
public class ExpiredAuthenticationToken extends AnonymousAuthenticationToken {

    private final JwtParseResult jwtParseResult;

    public static final String ROLE_EXPIRED_USER = "EXPIRED_USER";

    private static final Collection<GrantedAuthority> AUTHORITY = Collections.singleton(new SimpleGrantedAuthority(ROLE_EXPIRED_USER));

    public ExpiredAuthenticationToken(JwtParseResult jwtParseResult) {
        super(String.valueOf(jwtParseResult.getPrincipal().getId()), jwtParseResult.getPrincipal(), AUTHORITY);
        this.jwtParseResult = jwtParseResult;
    }

    public JwtParseResult getJwtParseResult() {
        return jwtParseResult;
    }
}
