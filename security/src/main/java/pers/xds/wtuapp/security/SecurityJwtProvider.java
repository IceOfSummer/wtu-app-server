package pers.xds.wtuapp.security;

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import pers.xds.wtuapp.security.bean.JwtParseResult;

import java.util.Collection;

/**
 * @author DeSen Xu
 * @date 2022-12-31 20:21
 */
public interface SecurityJwtProvider {

    /**
     * 生成用户凭据对应的jwt
     * @param uid 用户id
     * @param jwtId jwt唯一的id
     * @param authorities 权限
     * @return jwt字符串
     */
    String generateJwt(int uid, int jwtId, Collection<GrantedAuthority> authorities);

    /**
     * 生成用户凭据对应的jwt。
     * @param userPrincipal 用户凭据
     * @param jwtId jwtId
     * @return jwt字符串
     */
    String generateJwt(UserPrincipal userPrincipal, int jwtId);

    /**
     * 解析jwt
     * @param jwt jwt字符串
     * @return 用户凭据. 返回null表示jwt无效(若过期不会返回null)
     */
    @Nullable
    JwtParseResult parseJwt(String jwt);

}
