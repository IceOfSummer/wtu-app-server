package pers.xds.wtuapp.security;

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author DeSen Xu
 * @date 2022-12-31 20:21
 */
public interface SecurityJwtProvider {

    /**
     * 生成用户凭据对应的jwt
     * @param uid 用户id
     * @param username 用户名
     * @param authorities 权限
     * @return jwt字符串
     */
    String generateJwt(int uid, String username, Collection<GrantedAuthority> authorities);

    /**
     * 解析jwt
     * @param jwt jwt字符串
     * @return 用户凭据. 返回null表示jwt无效或者过期
     */
    @Nullable
    UserPrincipal parseJwt(String jwt);

}
