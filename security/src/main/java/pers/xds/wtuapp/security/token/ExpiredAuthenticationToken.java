package pers.xds.wtuapp.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.security.bean.JwtParseResult;

import java.util.Collections;

/**
 * 使用了过期的jwt令牌
 * @author DeSen Xu
 * @date 2023-01-01 15:23
 */
public class ExpiredAuthenticationToken extends AbstractAuthenticationToken {

    private final JwtParseResult jwtParseResult;

    public ExpiredAuthenticationToken(JwtParseResult jwtParseResult) {
        super(Collections.emptyList());
        this.jwtParseResult = jwtParseResult;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return jwtParseResult.getPrincipal();
    }

    public JwtParseResult getJwtParseResult() {
        return jwtParseResult;
    }
}
