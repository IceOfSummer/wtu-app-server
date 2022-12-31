package pers.xds.wtuapp.security.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import pers.xds.wtuapp.security.SecurityJwtProvider;
import pers.xds.wtuapp.security.UserPrincipal;

import java.util.*;

/**
 * @author DeSen Xu
 * @date 2022-12-31 20:24
 */
public class SecurityJwtProviderImpl implements SecurityJwtProvider {

    private final Algorithm algorithm;

    public SecurityJwtProviderImpl(String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    private static final String CLAIM_ID = "id";

    private static final String CLAIM_USERNAME = "username";

    private static final String CLAIM_ROLE = "role";

    @Override
    public String generateJwt(int uid, String username, Collection<GrantedAuthority> authorities) {
        List<String> list = new ArrayList<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            list.add(authority.getAuthority());
        }
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        return JWT.create()
                .withClaim(CLAIM_ID, uid)
                .withClaim(CLAIM_USERNAME, username)
                .withClaim(CLAIM_ROLE, list)
                .withExpiresAt(instance.getTime())
                // 当添加密码修改功能后，再将该项打开以判断jwt是否过期
//                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    @Override
    public UserPrincipal parseJwt(String jwt) {
        JWTVerifier verification = JWT.require(algorithm).build();
        DecodedJWT decodedJWT;
        try {
            decodedJWT = verification.verify(jwt);
        } catch (JWTVerificationException e) {
            return null;
        }
        Integer id = decodedJWT.getClaim(CLAIM_ID).asInt();
        String username = decodedJWT.getClaim(CLAIM_USERNAME).asString();
        List<String> roles = decodedJWT.getClaim(CLAIM_ROLE).asList(String.class);
        return new UserPrincipal(id, username, roles);
    }
}
