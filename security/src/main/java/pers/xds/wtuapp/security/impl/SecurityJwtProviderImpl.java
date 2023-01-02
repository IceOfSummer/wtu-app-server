package pers.xds.wtuapp.security.impl;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.springframework.security.core.GrantedAuthority;
import pers.xds.wtuapp.security.SecurityJwtProvider;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.security.bean.JwtParseResult;

import java.security.Key;
import java.util.*;

/**
 * @author DeSen Xu
 * @date 2022-12-31 20:24
 */
public class SecurityJwtProviderImpl implements SecurityJwtProvider {

    private static final String CLAIM_UID = "i";

    private static final String CLAIM_ROLE = "r";

    private static final String CLAIM_ID = "d";

    private final JwtConsumer jwtConsumer;

    private final int expireMinute;

    private final Key key;

    public SecurityJwtProviderImpl(String secret, int expireMinute) {
        this.key = new HmacKey(secret.getBytes());
        this.expireMinute = expireMinute;
        this.jwtConsumer = new JwtConsumerBuilder()
                .setVerificationKey(key)
                .setRequireIssuedAt()
                .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.HMAC_SHA256)
                .build();
    }


    @Override
    public String generateJwt(int uid, int jwtId, Collection<GrantedAuthority> authorities) {
        List<String> list = new ArrayList<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            list.add(authority.getAuthority());
        }
        JwtClaims claims = new JwtClaims();
        // 24小时后过期
        claims.setExpirationTimeMinutesInTheFuture(expireMinute);
        claims.setClaim(CLAIM_UID, uid);
        claims.setClaim(CLAIM_ID, jwtId);
        claims.setStringListClaim(CLAIM_ROLE, list);
        claims.setIssuedAt(NumericDate.now());

        JsonWebSignature jsonWebSignature = new JsonWebSignature();
        jsonWebSignature.setPayload(claims.toJson());
        jsonWebSignature.setKey(key);
        jsonWebSignature.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        try {
            return jsonWebSignature.getCompactSerialization();
        } catch (JoseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateJwt(UserPrincipal userPrincipal, int jwtId) {
        return generateJwt(userPrincipal.getId(), jwtId, userPrincipal.getAuthorities());
    }

    @Override
    public JwtParseResult parseJwt(String jwt) {
        JwtClaims jwtClaims;
        boolean expired = false;
        try {
            jwtClaims = jwtConsumer.processToClaims(jwt);
        } catch (InvalidJwtException e) {
            if (e.hasExpired() && e.getErrorDetails().size() == 1) {
                expired = true;
                jwtClaims = e.getJwtContext().getJwtClaims();
            } else {
                return null;
            }
        }
        try {
            UserPrincipal userPrincipal = new UserPrincipal(
                    Math.toIntExact(jwtClaims.getClaimValue(CLAIM_UID, Long.class)),
                    jwtClaims.getStringListClaimValue(CLAIM_ROLE)
            );

            return new JwtParseResult(
                    userPrincipal,
                    Math.toIntExact(jwtClaims.getClaimValue(CLAIM_ID, Long.class)),
                    jwtClaims.getIssuedAt().getValueInMillis(),
                    expired
            );
        } catch (MalformedClaimException e) {
            throw new RuntimeException(e);
        }
    }
}
