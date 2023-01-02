package pers.xds.wtuapp.security.bean;

import pers.xds.wtuapp.security.UserPrincipal;

/**
 * jwt解析结果
 * @author DeSen Xu
 * @date 2023-01-01 14:37
 */
public class JwtParseResult {

    /**
     * 用户凭据，若过期则该值为null
     */
    private final UserPrincipal principal;

    /**
     * jwtId
     */
    private final int jwtId;

    /**
     * 是否过期
     */
    private final boolean expired;

    private final long issuedAt;

    public JwtParseResult(UserPrincipal principal, int jwtId, long issuedAt, boolean expired) {
        this.principal = principal;
        this.jwtId = jwtId;
        this.expired = expired;
        this.issuedAt = issuedAt;
    }

    public UserPrincipal getPrincipal() {
        return principal;
    }

    public int getJwtId() {
        return jwtId;
    }

    public boolean isExpired() {
        return expired;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

}
