package pers.xds.wtuapp.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import pers.xds.wtuapp.security.UserPrincipal;

import java.util.Collection;

/**
 * jwt凭据
 * @author DeSen Xu
 * @date 2022-12-31 20:58
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal userPrincipal;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public JwtAuthenticationToken(UserPrincipal userPrincipal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return userPrincipal;
    }
}
